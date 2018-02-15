/*
 *
 * Copyright 2018 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package uk.ac.ebi.ampt2d.accession.common.accessioning;

import uk.ac.ebi.ampt2d.accession.common.utils.DigestFunction;
import uk.ac.ebi.ampt2d.accession.common.utils.HashingFunction;
import uk.ac.ebi.ampt2d.accession.common.persistence.MonotonicDatabaseService;
import uk.ac.ebi.ampt2d.accession.common.generators.monotonic.MonotonicAccessionGenerator;
import uk.ac.ebi.ampt2d.accession.common.generators.monotonic.MonotonicRange;

import java.util.Collection;

public class BasicMonotonicAccessioningService<MODEL, HASH> extends BasicAccessioningService<MODEL, HASH, Long> {

    public BasicMonotonicAccessioningService(MonotonicAccessionGenerator<MODEL> accessionGenerator,
                                             MonotonicDatabaseService<MODEL, HASH> dbService,
                                             DigestFunction<MODEL> digestFunction,
                                             HashingFunction<HASH> hashingFunction) {
        super(accessionGenerator, dbService, digestFunction, hashingFunction);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Collection<MonotonicRange> availableRanges = getAccessionGenerator().getAvailableRanges();
        getAccessionGenerator().recoverState(getDbService().getExistingIds(availableRanges));
    }

    @Override
    protected MonotonicAccessionGenerator<MODEL> getAccessionGenerator() {
        return (MonotonicAccessionGenerator<MODEL>) super.getAccessionGenerator();
    }

    @Override
    protected MonotonicDatabaseService<MODEL, HASH> getDbService() {
        return (MonotonicDatabaseService<MODEL, HASH>) super.getDbService();
    }

}
