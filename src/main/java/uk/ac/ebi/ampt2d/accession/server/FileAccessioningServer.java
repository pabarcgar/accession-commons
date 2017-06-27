/*
 *
 * Copyright 2017 EMBL - European Bioinformatics Institute
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
package uk.ac.ebi.ampt2d.accession.server;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.ebi.ampt2d.accession.AccessionGenerator;
import uk.ac.ebi.ampt2d.accession.AccessioningProperties;
import uk.ac.ebi.ampt2d.accession.file.File;
import uk.ac.ebi.ampt2d.accession.file.FileAccessionRepository;
import uk.ac.ebi.ampt2d.accession.file.FileAccessioningService;
import uk.ac.ebi.ampt2d.accession.file.UuidFile;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "/v1/accession")
public class FileAccessioningServer {

    private FileAccessioningService accessioningService;

    public FileAccessioningServer(@Autowired FileAccessionRepository accessionRepository,
                                  @Autowired AccessioningProperties properties,
                                  @Autowired AccessionGenerator<File, UuidFile> generator) {
        this.accessioningService = new FileAccessioningService(accessionRepository, generator);
    }

    @RequestMapping(value = "/file", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public FileAccessionResponse accessionFiles(@RequestBody List<File> files) {
        Map<File, UUID> accessions = accessioningService.getAccessions(files);

        return new FileAccessionResponse(accessions);
    }
}
