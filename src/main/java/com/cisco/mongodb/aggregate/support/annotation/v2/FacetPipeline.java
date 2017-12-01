/*
 *  Copyright (c) 2017 the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 *
 */

package com.cisco.mongodb.aggregate.support.annotation.v2;

import com.cisco.mongodb.aggregate.support.annotation.Conditional;

import java.lang.annotation.*;

import static com.cisco.mongodb.aggregate.support.annotation.Conditional.*;

/**
 * Created by rkolliva
 * 4/1/17.
 *
 */
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Repeatable(FacetPipelines.class)
public @interface FacetPipeline {

  String name();

  /**
   * An array of pipeline stages - allows each stage of the pipeline in a facet
   * to be specified independently.  Promotes readability.  If query() is also
   * specified - this is ignored.
   */
  FacetPipelineStage [] stages() default {};

  /**
   * A query string for the pipeline stage.  Mutually exclusive with FacetPipelineStage [].
   * Basically replicates the behavior of the @Facet annotation.  Use of this takes precedence
   * over the FacetPipelineStage
   *
   * @return - The string to use for this pipeline
   *
   */
  String query() default "";

  /**
   * An optional conditional that determines whether this pipeline stage should be included
   * in the facet or not.
   *
   * @return - A condition class that evaluates the conditional.
   *
   */
  Conditional[] condition() default {};

  /**
   * An optional conditional type to determines whether all the conditions should be true or any one of them
   *
   * @return - A condition class that evaluates the conditional.
   *
   */
  ConditionalMatchType conditionMatchType() default ConditionalMatchType.ANY;
}
