/*
 * Copyright © 2019 Apple Inc. and the ServiceTalk project authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.servicetalk.grpc.api;

import io.servicetalk.concurrent.api.Executor;
import io.servicetalk.concurrent.api.Publisher;
import io.servicetalk.concurrent.api.Single;
import io.servicetalk.http.api.HttpExecutionStrategy;

import static java.util.Objects.requireNonNull;

final class DefaultGrpcExecutionStrategy implements GrpcExecutionStrategy {

    private final HttpExecutionStrategy delegate;

    DefaultGrpcExecutionStrategy(final HttpExecutionStrategy delegate) {
        this.delegate = requireNonNull(delegate);
    }

    @Override
    public boolean isMetadataReceiveOffloaded() {
        return delegate.isMetadataReceiveOffloaded();
    }

    @Override
    public boolean isDataReceiveOffloaded() {
        return delegate.isDataReceiveOffloaded();
    }

    @Override
    public boolean isSendOffloaded() {
        return delegate.isSendOffloaded();
    }

    @Override
    public HttpExecutionStrategy merge(final HttpExecutionStrategy other) {
        return delegate.merge(other);
    }

    @Override
    public <T> Single<T> offloadSend(final Executor executor, final Single<T> original) {
        return delegate.offloadSend(executor, original);
    }

    @Override
    public <T> Single<T> offloadReceive(final Executor executor, final Single<T> original) {
        return delegate.offloadReceive(executor, original);
    }

    @Override
    public <T> Publisher<T> offloadSend(final Executor executor, final Publisher<T> original) {
        return delegate.offloadSend(executor, original);
    }

    @Override
    public <T> Publisher<T> offloadReceive(final Executor executor, final Publisher<T> original) {
        return delegate.offloadReceive(executor, original);
    }

    @Override
    public String toString() {
        return delegate.toString();
    }
}
