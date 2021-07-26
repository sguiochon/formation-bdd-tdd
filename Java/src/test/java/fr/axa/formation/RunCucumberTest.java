/*
 * Copyright (c) 2019 AXA France.
 * Licensed under the AXA France License (the "License");
 * you may not use this file except in compliance with the License.
 * A copy of the License can be found in the LICENSE.TXT file
 * distributed together with this file.
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package fr.axa.formation;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features",
		plugin = {"pretty", "html:target/cucumber/bagbasics"},
		glue = "fr.axa.formation",
		tags = "not @ignore")
public class RunCucumberTest {
}

