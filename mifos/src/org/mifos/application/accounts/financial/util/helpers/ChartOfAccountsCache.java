/**

 * COACache.java    version: 1.0

 

 * Copyright (c) 2005-2006 Grameen Foundation USA

 * 1029 Vermont Avenue, NW, Suite 400, Washington DC 20005

 * All rights reserved.

 

 * Apache License 
 * Copyright (c) 2005-2006 Grameen Foundation USA 
 * 

 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at http://www.apache.org/licenses/LICENSE-2.0 
 *

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the 

 * License. 
 * 
 * See also http://www.apache.org/licenses/LICENSE-2.0.html for an explanation of the license 

 * and how it is applied.  

 *

 */

package org.mifos.application.accounts.financial.util.helpers;

import java.util.HashMap;
import java.util.Map;

import org.mifos.application.accounts.financial.business.COABO;
import org.mifos.application.accounts.financial.exceptions.FinancialException;
import org.mifos.application.accounts.financial.exceptions.FinancialExceptionConstants;

public class ChartOfAccountsCache {
	private static Map<Short, COABO> cache = new HashMap<Short, COABO>();

	public static boolean isInitialized() {
		return !cache.isEmpty();
	}
	
	public static void add(COABO coa) {
		if (coa == null) {
			throw new RuntimeException("Got a null coa reference");
		}
		if (cache.get(coa.getCategoryId()) == null) {
			cache.put(coa.getCategoryId(), coa);
		} else {
			throw new RuntimeException("ChartOfAcctionsCache already contains an account with id: " + coa.getCategoryId());
		}
	}

	
	public static COABO get(short categoryId) throws FinancialException {
		COABO category = cache.get(categoryId);
		if (category == null) {
			throw new FinancialException(
					FinancialExceptionConstants.CATEGORYNOTFOUND);
		}

		return category;
	}

}
