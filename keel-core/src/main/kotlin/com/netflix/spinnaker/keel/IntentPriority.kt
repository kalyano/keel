/*
 * Copyright 2017 Netflix, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.netflix.spinnaker.keel

/**
 * Lower priority intents have a higher chance of being evicted from a scheduling cycle. Intents should be evicted
 * either via filters in response to manual operator action or system events.
 *
 * As a use-case, if you're seeing too much load being generated by keel, you can configure lower-priority intents to
 * converge less frequently and distribute them evenly over an hour. A critical intent, however, would have an SLA to
 * always converge at the next cycle.
 */
enum class IntentPriority {
  CRITICAL, HIGH, NORMAL, LOW
}

enum class PriorityMatcher {
  EQUAL, EQUAL_GT, EQUAL_LT
}