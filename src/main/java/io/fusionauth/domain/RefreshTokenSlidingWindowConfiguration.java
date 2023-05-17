/*
 * Copyright (c) 2023, FusionAuth, All Rights Reserved
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

import java.util.Objects;

import com.inversoft.json.JacksonConstructor;
import com.inversoft.json.ToString;

/**
 * @author Daniel DeGroff
 */
public class RefreshTokenSlidingWindowConfiguration implements Buildable<RefreshTokenSlidingWindowConfiguration> {
  public int maximumTimeToLiveInMinutes = 30 * 24 * 60;

  @JacksonConstructor
  public RefreshTokenSlidingWindowConfiguration() {
  }

  public RefreshTokenSlidingWindowConfiguration(RefreshTokenSlidingWindowConfiguration other) {
    this.maximumTimeToLiveInMinutes = other.maximumTimeToLiveInMinutes;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RefreshTokenSlidingWindowConfiguration that = (RefreshTokenSlidingWindowConfiguration) o;
    return maximumTimeToLiveInMinutes == that.maximumTimeToLiveInMinutes;
  }

  @Override
  public int hashCode() {
    return Objects.hash(maximumTimeToLiveInMinutes);
  }

  @Override
  public String toString() {
    return ToString.toString(this);
  }
}
