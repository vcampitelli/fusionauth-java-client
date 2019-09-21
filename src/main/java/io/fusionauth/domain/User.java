/*
 * Copyright (c) 2018-2019, FusionAuth, All Rights Reserved
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 */
package io.fusionauth.domain;

import java.net.URI;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inversoft.json.ToString;
import io.fusionauth.domain.internal._InternalJSONColumn;
import io.fusionauth.domain.internal.annotation.InternalJSONColumn;
import io.fusionauth.domain.util.Normalizer;
import static io.fusionauth.domain.util.Normalizer.toLowerCase;
import static io.fusionauth.domain.util.Normalizer.trim;

/**
 * The global view of a User. This object contains all global information about the user including birth date, registration information
 * preferred languages, global attributes, etc.
 *
 * @author Seth Musselman
 */
public class User extends SecureIdentity implements Buildable<User>, _InternalJSONColumn, Tenantable {
  @InternalJSONColumn
  public final List<Locale> preferredLanguages = new ArrayList<>();

  private final List<GroupMember> memberships = new ArrayList<>();

  private final List<UserRegistration> registrations = new ArrayList<>();

  public boolean active;

  public LocalDate birthDate;

  public UUID cleanSpeakId;

  public Map<String, Object> data = new LinkedHashMap<>();

  public String email;

  public ZonedDateTime expiry;

  public String firstName;

  public String fullName;

  public URI imageUrl;

  public ZonedDateTime insertInstant;

  public ZonedDateTime lastLoginInstant;

  public String lastName;

  public String middleName;

  public String mobilePhone;

  public String parentEmail;

  public UUID tenantId;

  public ZoneId timezone;

  public TwoFactorDelivery twoFactorDelivery;

  public boolean twoFactorEnabled;

  public String twoFactorSecret;

  public String username;

  public ContentStatus usernameStatus;

  public User() {
  }

  public User(User user) {
    this.active = user.active;
    this.birthDate = user.birthDate;
    this.cleanSpeakId = user.cleanSpeakId;
    this.email = user.email;
    this.encryptionScheme = user.encryptionScheme;
    this.expiry = user.expiry;
    this.factor = user.factor;
    this.firstName = user.firstName;
    this.fullName = user.fullName;
    this.id = user.id;
    this.imageUrl = user.imageUrl;
    this.insertInstant = user.insertInstant;
    this.lastLoginInstant = user.lastLoginInstant;
    this.lastName = user.lastName;
    this.memberships.addAll(user.memberships.stream().map(GroupMember::new).collect(Collectors.toList()));
    this.middleName = user.middleName;
    this.mobilePhone = user.mobilePhone;
    this.parentEmail = user.parentEmail;
    this.password = user.password;
    this.passwordChangeRequired = user.passwordChangeRequired;
    this.passwordLastUpdateInstant = user.passwordLastUpdateInstant;
    this.preferredLanguages.addAll(user.preferredLanguages);
    this.registrations.addAll(user.registrations.stream().map(UserRegistration::new).collect(Collectors.toList()));
    this.salt = user.salt;
    this.tenantId = user.tenantId;
    this.timezone = user.timezone;
    this.twoFactorDelivery = user.twoFactorDelivery;
    this.twoFactorEnabled = user.twoFactorEnabled;
    this.twoFactorSecret = user.twoFactorSecret;
    this.username = user.username;
    this.usernameStatus = user.usernameStatus;
    this.verified = user.verified;

    if (user.data != null) {
      this.data.putAll(user.data);
    }
  }

  /**
   * Safely adds a membership to a user. This ensures no duplicates.
   *
   * @param member The member.
   */
  public void addMemberships(GroupMember member) {
    memberships.removeIf(m -> m.groupId.equals(member.groupId));
    memberships.add(member);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (!(o instanceof User)) {
      return false;
    }
    User user = (User) o;
    sort();
    user.sort();
    return super.equals(o) &&
        Objects.equals(active, user.active) &&
        Objects.equals(birthDate, user.birthDate) &&
        Objects.equals(cleanSpeakId, user.cleanSpeakId) &&
        Objects.equals(data, user.data) &&
        Objects.equals(email, user.email) &&
        Objects.equals(expiry, user.expiry) &&
        Objects.equals(firstName, user.firstName) &&
        Objects.equals(fullName, user.fullName) &&
        Objects.equals(imageUrl, user.imageUrl) &&
        Objects.equals(insertInstant, user.insertInstant) &&
        Objects.equals(lastLoginInstant, user.lastLoginInstant) &&
        Objects.equals(lastName, user.lastName) &&
        Objects.equals(memberships, user.memberships) &&
        Objects.equals(middleName, user.middleName) &&
        Objects.equals(mobilePhone, user.mobilePhone) &&
        Objects.equals(registrations, user.registrations) &&
        Objects.equals(parentEmail, user.parentEmail) &&
        Objects.equals(tenantId, user.tenantId) &&
        Objects.equals(timezone, user.timezone) &&
        Objects.equals(twoFactorDelivery, user.twoFactorDelivery) &&
        Objects.equals(twoFactorEnabled, user.twoFactorEnabled) &&
        Objects.equals(twoFactorSecret, user.twoFactorSecret) &&
        Objects.equals(username, user.username) &&
        Objects.equals(usernameStatus, user.usernameStatus);
  }

  @JsonIgnore
  public int getAge() {
    if (birthDate == null) {
      return -1;
    }

    return (int) birthDate.until(LocalDate.now(), ChronoUnit.YEARS);
  }

  /**
   * @return return a single identity value preferring email over username.
   */
  @JsonIgnore
  public String getLogin() {
    return email == null ? username : email;
  }

  public List<GroupMember> getMemberships() {
    return memberships;
  }

  @JsonIgnore
  public String getName() {
    if (fullName != null) {
      return fullName;
    }
    if (firstName != null) {
      return firstName + (lastName != null ? " " + lastName : "");
    }

    return null;
  }

  public UserRegistration getRegistrationForApplication(UUID id) {
    return getRegistrations().stream()
                             .filter(reg -> reg.applicationId.equals(id))
                             .findFirst()
                             .orElse(null);
  }

  public List<UserRegistration> getRegistrations() {
    return registrations;
  }

  public Set<String> getRoleNamesForApplication(UUID id) {
    UserRegistration registration = getRegistrationForApplication(id);
    return registration != null ? registration.roles : null;
  }

  @Override
  public UUID getTenantId() {
    return tenantId;
  }

  /**
   * Return true if user data is provided for this user or any registrations.
   *
   * @return true if user data exists.
   */
  public boolean hasUserData() {
    if (!data.isEmpty()) {
      return true;
    }

    for (UserRegistration userRegistration : registrations) {
      if (userRegistration.hasRegistrationData()) {
        return true;
      }
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), active, birthDate, cleanSpeakId, data, email, expiry, firstName, fullName, imageUrl, insertInstant,
                        lastLoginInstant, lastName, memberships, middleName, mobilePhone, registrations, parentEmail, tenantId, timezone,
                        twoFactorDelivery, twoFactorEnabled, twoFactorSecret, username, usernameStatus);
  }

  /**
   * Attempt to retrieve the users email address first by checking the top level and then in user data.
   *
   * @return an email address or null if no email address is found.
   */
  public String lookupEmail() {
    if (email != null) {
      return email;
    } else if (data.containsKey("email")) {
      return data.get("email").toString();
    }
    return null;
  }

  public Locale lookupPreferredLanguage(UUID applicationId) {
    for (UserRegistration registration : registrations) {
      if (registration.applicationId.equals(applicationId)) {
        if (registration.preferredLanguages.size() > 0) {
          return registration.preferredLanguages.get(0);
        }
      }
    }

    if (preferredLanguages.size() > 0) {
      return preferredLanguages.get(0);
    }

    return null;
  }

  /**
   * Normalizes all of the fields.
   */
  public void normalize() {
    Normalizer.removeEmpty(data);
    email = toLowerCase(trim(email));
    encryptionScheme = trim(encryptionScheme);
    firstName = trim(firstName);
    fullName = trim(fullName);
    lastName = trim(lastName);
    middleName = trim(middleName);
    mobilePhone = trim(mobilePhone);
    parentEmail = toLowerCase(trim(parentEmail));
    preferredLanguages.removeIf(Objects::isNull);
    username = trim(username);
    if (username != null && username.length() == 0) {
      username = null;
    }
    getRegistrations().forEach(UserRegistration::normalize);
  }

  public void removeMembershipById(UUID groupId) {
    memberships.removeIf(m -> m.groupId.equals(groupId));
  }

  /**
   * Clear out sensitive data. Password, salt, etc.
   */
  public User secure() {
    encryptionScheme = null;
    factor = null;
    password = null;
    salt = null;
    twoFactorSecret = null;
    return this;
  }

  public User sort() {
    this.registrations.sort(Comparator.comparing(ur -> ur.applicationId));
    return this;
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
