package org.mifos.application.customer.center.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.mifos.application.NamedQueryConstants;
import org.mifos.application.customer.center.business.CenterBO;
import org.mifos.application.customer.util.helpers.CustomerConstants;
import org.mifos.framework.exceptions.PersistenceException;
import org.mifos.framework.persistence.Persistence;

public class CenterPersistence extends  Persistence{
	
	public boolean isCenterExists(String name) throws PersistenceException{
		Map<String, Object> queryParameters = new HashMap<String, Object>();
		queryParameters.put(CustomerConstants.DISPLAY_NAME, name);
		List queryResult = executeNamedQuery(NamedQueryConstants.GET_CENTER_COUNT_BY_NAME, queryParameters);
		return ((Integer)queryResult.get(0)).intValue()>0;
	}

	public CenterBO getCenter(Integer customerId) throws PersistenceException{
		return (CenterBO) getPersistentObject(CenterBO.class,customerId);
	}
	
	public CenterBO getCenterBySystemId(String globalCustNum) throws PersistenceException{
		Map<String, String> queryParameters = new HashMap<String, String>();
		CenterBO center = null;
		queryParameters.put("globalCustNum", globalCustNum);
			List<CenterBO> queryResult = executeNamedQuery(
						NamedQueryConstants.GET_CENTER_BY_SYSTEMID,
						queryParameters);
				if (null != queryResult && queryResult.size() > 0) {
					center = queryResult.get(0);
				}
		return center;
	}
}
