/*
 * Copyright (c) 2018, FusionAuth, All Rights Reserved
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
package io.fusionauth.domain.api;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.inversoft.json.JacksonConstructor;
import io.fusionauth.domain.GroupMember;

/**
 * Group Member Request
 *
 * @author Daniel DeGroff
 */
public class MemberRequest {
  public Map<UUID, List<GroupMember>> members;

  @JacksonConstructor
  public MemberRequest() {
    this.members = new LinkedHashMap<>();
  }

  public MemberRequest(Map<UUID, List<GroupMember>> members) {
    this.members = members;
  }

  public MemberRequest(UUID groupId, List<GroupMember> members) {
    this.members = new HashMap<>(1);
    this.members.put(groupId, members);
  }
}
