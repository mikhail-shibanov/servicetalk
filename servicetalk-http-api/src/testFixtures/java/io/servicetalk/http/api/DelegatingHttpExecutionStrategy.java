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
package io.servicetalk.http.api;

import io.servicetalk.concurrent.api.Executor;
import io.servicetalk.concurrent.api.Publisher;
import io.servicetalk.concurrent.api.Single;

import static java.util.Objects.requireNonNull;

/**
 * An {@link HttpExecutionStrategy} that delegates all method calls to another {@link HttpExecutionStrategy}.
 */
public class DelegatingHttpExecutionStrategy implements HttpExecutionStrategy {

    private final HttpExecutionStrategy delegate;

    /**
     * Create a new instance.
     *
     * @param delegate {@link HttpExecutionStrategy} to which all method calls will be delegated.
     */
    public DelegatingHttpExecutionStrategy(final HttpExecutionStrategy delegate) {
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
        // Since any methods can be overridden to change behavior, we leverage the other strategy to also account for
        // the overridden methods here.
        return other.merge(this);
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
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final DelegatingHttpExecutionStrategy that = (DelegatingHttpExecutionStrategy) o;

        return delegate.equals(that.delegate);
    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }
}
