/*
 * Copyright (c) 2018-2023, FusionAuth, All Rights Reserved
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
package io.fusionauth.domain.search;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.UUID;

/**
 * @author Brian Pontarelli
 */
public abstract class BaseElasticSearchCriteria extends BaseSearchCriteria {
  public boolean accurateTotal;

  public List<UUID> ids = new ArrayList<>();

  public String nextResults;

  public String query;

  public String queryString;

  public List<SortField> sortFields = new ArrayList<>();

  @Override
  public BaseElasticSearchCriteria prepare() {
    return this;
  }

  @Override
  public Set<String> supportedOrderByColumns() {
    // Return empty set since any Elasticsearch query will have service level validation on sorting.
    return Collections.emptySet();
  }
}
