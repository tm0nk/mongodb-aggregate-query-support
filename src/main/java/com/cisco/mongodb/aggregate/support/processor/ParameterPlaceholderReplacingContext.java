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

package com.cisco.mongodb.aggregate.support.processor;

import com.cisco.mongodb.aggregate.support.query.AbstractAggregateQueryProvider;
import com.cisco.mongodb.aggregate.support.query.AbstractAggregateQueryProvider.AggregationStage;
import com.cisco.mongodb.aggregate.support.query.QueryProvider;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Created by rkolliva
 * 4/2/17.
 */


public class ParameterPlaceholderReplacingContext implements QueryProcessorContext {

  private final Annotation annotation;

  private final AggregationStage aggregationStage;

  private final AbstractAggregateQueryProvider queryProvider;

  private final Method method;

  public ParameterPlaceholderReplacingContext(AbstractAggregateQueryProvider queryProvider,
                                              Method method, AggregationStage aggregationStage,
                                              Annotation annotation) {

    this.aggregationStage = aggregationStage;
    this.annotation = annotation;
    this.queryProvider = queryProvider;
    this.method = method;
  }

  public ParameterPlaceholderReplacingContext(QueryProcessorContext pctx, AggregationStage stage, Annotation annotation) {
    Assert.isAssignable(ParameterPlaceholderReplacingContext.class, pctx.getClass(),
                        "Expecting ParameterPlaceholderReplacingContext");
    ParameterPlaceholderReplacingContext parentContext = (ParameterPlaceholderReplacingContext) pctx;
    this.aggregationStage = stage;
    this.annotation = annotation;
    this.queryProvider = (AbstractAggregateQueryProvider) parentContext.queryProvider();
    this.method = parentContext.method;
  }

  @Override
  public String getQuery() {
    return queryProvider.getQueryString(aggregationStage, queryProvider.getQueryForStage(annotation));
  }

  @Override
  public Annotation getAnnotation() {
    return annotation;
  }

  @Override
  public Method getMethod() {
    return method;
  }

  @Override
  public AggregationStage getAggregationStage() {
    return aggregationStage;
  }

  @Override
  public QueryProvider queryProvider() {
    return queryProvider;
  }
}
