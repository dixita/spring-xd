/*
 * Copyright 2009-2013 the original author or authors.
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
 */
package org.springframework.xd.shell.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.Test;
import org.springframework.util.FileCopyUtils;

/**
 * @author Gunnar Hillert
 * @since 1.0
 */
public class UiUtilsTest {

	@Test
	public void testRenderTextTable() {

		final Table table = new Table();
		table.addHeader(1, new TableHeader("Tap Name"))
		     .addHeader(2, new TableHeader("Stream Name"))
		     .addHeader(3, new TableHeader("Tap Definition"));

		for (int i = 1; i<=3; i++) {
			final TableRow row = new TableRow();
			row.addValue(1, "tap" + i)
			   .addValue(2, "ticktock")
			   .addValue(3, "tap@ticktock|log");
			table.getRows().add(row);
		}

		String expectedTableAsString = null;

		final InputStream inputStream = getClass()
				.getClassLoader()
				.getResourceAsStream("testRenderTextTable-expected-output.txt");

		assertNotNull("The inputstream is null.", inputStream);

		try {
			expectedTableAsString = FileCopyUtils.copyToString(new InputStreamReader(inputStream));
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}

		final String tableRenderedAsString = UiUtils.renderTextTable(table);

		assertEquals(expectedTableAsString, tableRenderedAsString);
	}

}