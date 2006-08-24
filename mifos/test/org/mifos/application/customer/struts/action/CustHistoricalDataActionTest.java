package org.mifos.application.customer.struts.action;

import java.net.URISyntaxException;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import org.mifos.application.customer.business.CustomerHistoricalDataEntity;
import org.mifos.application.customer.center.business.CenterBO;
import org.mifos.application.customer.client.business.ClientBO;
import org.mifos.application.customer.client.util.helpers.ClientConstants;
import org.mifos.application.customer.exceptions.CustomerException;
import org.mifos.application.customer.group.business.GroupBO;
import org.mifos.application.customer.group.util.helpers.GroupConstants;
import org.mifos.application.customer.util.helpers.CustomerConstants;
import org.mifos.application.meeting.business.MeetingBO;
import org.mifos.application.util.helpers.ActionForwards;
import org.mifos.framework.MifosMockStrutsTestCase;
import org.mifos.framework.hibernate.helper.HibernateUtil;
import org.mifos.framework.security.util.ActivityContext;
import org.mifos.framework.security.util.UserContext;
import org.mifos.framework.struts.tags.DateHelper;
import org.mifos.framework.util.helpers.Constants;
import org.mifos.framework.util.helpers.DateUtils;
import org.mifos.framework.util.helpers.Money;
import org.mifos.framework.util.helpers.ResourceLoader;
import org.mifos.framework.util.helpers.SessionUtils;
import org.mifos.framework.util.helpers.TestObjectFactory;

public class CustHistoricalDataActionTest extends MifosMockStrutsTestCase {

	private ClientBO client;

	private GroupBO group;

	private CenterBO center;

	private MeetingBO meeting;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		try {
			setServletConfigFile(ResourceLoader.getURI("WEB-INF/web.xml")
					.getPath());
			setConfigFile(ResourceLoader.getURI(
					"org/mifos/framework/util/helpers/struts-config.xml")
					.getPath());
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		UserContext userContext = TestObjectFactory.getUserContext();
		request.getSession().setAttribute(Constants.USERCONTEXT, userContext);
		addRequestParameter("recordLoanOfficerId", "1");
		addRequestParameter("recordOfficeId", "1");
		ActivityContext ac = new ActivityContext((short) 0, userContext
				.getBranchId().shortValue(), userContext.getId().shortValue());
		request.getSession(false).setAttribute("ActivityContext", ac);
	}

	@Override
	public void tearDown() throws Exception {
		TestObjectFactory.cleanUp(client);
		TestObjectFactory.cleanUp(group);
		TestObjectFactory.cleanUp(center);
		HibernateUtil.closeSession();
		super.tearDown();
	}

	public void testGetWhenCustHistoricalDataIsNull() {
		createInitialObjects();
		setRequestPathInfo("/custHistoricalDataAction.do");
		addRequestParameter("method", "get");
		addRequestParameter("globalCustNum", client.getGlobalCustNum());
		getRequest().getSession().setAttribute("security_param", "Client");
		actionPerform();
		verifyForward(ActionForwards.get_success.toString());
		verifyNoActionErrors();
		verifyNoActionMessages();
		assertEquals(new java.sql.Date(DateUtils
				.getCurrentDateWithoutTimeStamp().getTime()).toString(), request.getSession().getAttribute(CustomerConstants.MFIJOININGDATE).toString());
	}

	public void testGetWhenCustHistoricalDataIsNotNull()
			throws CustomerException {
		createInitialObjects();
		CustomerHistoricalDataEntity customerHistoricalDataEntity = new CustomerHistoricalDataEntity(
				client);
		customerHistoricalDataEntity.setMfiJoiningDate(offSetCurrentDate(10));
		Date mfiDate = new Date(customerHistoricalDataEntity.getMfiJoiningDate().getTime());
		client.updateHistoricalData(customerHistoricalDataEntity);
		client.update();
		HibernateUtil.commitTransaction();
		assertEquals(mfiDate,new Date(client.getMfiJoiningDate().getTime()));
		SessionUtils.setAttribute(Constants.BUSINESS_KEY, client, request
				.getSession());
		setRequestPathInfo("/custHistoricalDataAction.do");
		addRequestParameter("method", "get");
		addRequestParameter("globalCustNum", client.getGlobalCustNum());
		getRequest().getSession().setAttribute("security_param", "Client");
		actionPerform();
		verifyForward(ActionForwards.get_success.toString());
		verifyNoActionErrors();
		verifyNoActionMessages();
		assertEquals(new java.sql.Date(DateUtils
				.getDateWithoutTimeStamp(mfiDate.getTime()).getTime()).toString(), request.getSession().getAttribute(CustomerConstants.MFIJOININGDATE).toString());
	}

	public void testLoadWhenCustHistoricalDataIsNull() {
		createInitialObjects();
		
		setRequestPathInfo("/custHistoricalDataAction.do");
		addRequestParameter("method", "get");
		addRequestParameter("globalCustNum", client.getGlobalCustNum());
		getRequest().getSession().setAttribute("security_param", "Client");
		actionPerform();
		verifyForward(ActionForwards.get_success.toString());

		setRequestPathInfo("/custHistoricalDataAction.do");
		addRequestParameter("method", "load");
		getRequest().getSession().setAttribute("security_param", "Client");
		actionPerform();
		verifyForward(ActionForwards.load_success.toString());
		verifyNoActionErrors();
		verifyNoActionMessages();
	}

	public void testPreview() {
		createInitialObjects();
		setRequestPathInfo("/custHistoricalDataAction.do");
		addRequestParameter("method", "preview");
		actionPerform();
		verifyForward(ActionForwards.preview_success.toString());
		verifyNoActionErrors();
		verifyNoActionMessages();
	}

	public void testPrevious() {
		createInitialObjects();
		setRequestPathInfo("/custHistoricalDataAction.do");
		addRequestParameter("method", "previous");
		actionPerform();
		verifyForward(ActionForwards.previous_success.toString());
		verifyNoActionErrors();
		verifyNoActionMessages();
	}

	public void testCancel() {
		createInitialObjects();
		setRequestPathInfo("/custHistoricalDataAction.do");
		addRequestParameter("method", "cancel");
		addRequestParameter("type", "Client");
		actionPerform();
		verifyForward(ActionForwards.client_detail_page.toString());
		verifyNoActionErrors();
		verifyNoActionMessages();
	}

	public void testUpdateWhenCustHistoricalDataIsNull() {
		createInitialObjects();
		SessionUtils.setAttribute(Constants.BUSINESS_KEY, client, request
				.getSession());
		setRequestPathInfo("/custHistoricalDataAction.do");
		addRequestParameter("method", "update");
		addRequestParameter("productName", "Test");
		addRequestParameter("loanAmount", "100");
		addRequestParameter("totalAmountPaid", "50");
		addRequestParameter("interestPaid", "10");
		addRequestParameter("missedPaymentsCount", "1");
		addRequestParameter("totalPaymentsCount", "2");
		addRequestParameter("commentNotes", "Test notes");
		addRequestParameter("loanCycleNumber", "1");
		addRequestParameter("type", "Client");
		addRequestParameter("mfiJoiningDate", DateHelper
				.getCurrentDate(((UserContext) request.getSession()
						.getAttribute("UserContext")).getPereferedLocale()));
		actionPerform();
		verifyForward(ActionForwards.update_success.toString());
		verifyNoActionErrors();
		verifyNoActionMessages();
		client = (ClientBO) TestObjectFactory.getObject(ClientBO.class, client
				.getCustomerId());
		assertEquals("Test", client.getHistoricalData().getProductName());
		assertEquals("Test notes", client.getHistoricalData().getNotes());
		assertEquals(new Money("100"), client.getHistoricalData()
				.getLoanAmount());
		assertEquals(new Money("50"), client.getHistoricalData()
				.getTotalAmountPaid());
		assertEquals(new Money("10"), client.getHistoricalData()
				.getInterestPaid());
		assertEquals(1, client.getHistoricalData().getMissedPaymentsCount()
				.intValue());
		assertEquals(2, client.getHistoricalData().getTotalPaymentsCount()
				.intValue());
		assertEquals(1, client.getHistoricalData().getLoanCycleNumber()
				.intValue());
	}

	public void testUpdateWhenCustHistoricalDataIsNotNull()
			throws CustomerException {
		createInitialObjects();
		CustomerHistoricalDataEntity customerHistoricalDataEntity = new CustomerHistoricalDataEntity(
				client);
		client.updateHistoricalData(customerHistoricalDataEntity);
		client.update();
		HibernateUtil.commitTransaction();

		SessionUtils.setAttribute(Constants.BUSINESS_KEY, client, request
				.getSession());
		setRequestPathInfo("/custHistoricalDataAction.do");
		addRequestParameter("method", "update");
		addRequestParameter("productName", "Test");
		addRequestParameter("loanAmount", "200");
		addRequestParameter("totalAmountPaid", "150");
		addRequestParameter("interestPaid", "50");
		addRequestParameter("missedPaymentsCount", "2");
		addRequestParameter("totalPaymentsCount", "3");
		addRequestParameter("commentNotes", "Test notes");
		addRequestParameter("loanCycleNumber", "2");
		addRequestParameter("type", "Client");
		addRequestParameter("mfiJoiningDate", DateHelper
				.getCurrentDate(((UserContext) request.getSession()
						.getAttribute("UserContext")).getPereferedLocale()));
		actionPerform();
		verifyForward(ActionForwards.update_success.toString());
		verifyNoActionErrors();
		verifyNoActionMessages();
		client = (ClientBO) TestObjectFactory.getObject(ClientBO.class, client
				.getCustomerId());
		assertEquals("Test", client.getHistoricalData().getProductName());
		assertEquals("Test notes", client.getHistoricalData().getNotes());
		assertEquals(new Money("200"), client.getHistoricalData()
				.getLoanAmount());
		assertEquals(new Money("150"), client.getHistoricalData()
				.getTotalAmountPaid());
		assertEquals(new Money("50"), client.getHistoricalData()
				.getInterestPaid());
		assertEquals(2, client.getHistoricalData().getMissedPaymentsCount()
				.intValue());
		assertEquals(3, client.getHistoricalData().getTotalPaymentsCount()
				.intValue());
		assertEquals(2, client.getHistoricalData().getLoanCycleNumber()
				.intValue());
	}

	private void createInitialObjects() {
		meeting = TestObjectFactory.createMeeting(TestObjectFactory
				.getMeetingHelper(1, 1, 4, 2));
		center = TestObjectFactory.createCenter("Center", Short.valueOf("13"),
				"1.4", meeting, new Date(System.currentTimeMillis()));
		group = TestObjectFactory.createGroup("Group", GroupConstants.ACTIVE,
				"1.4.1", center, new Date(System.currentTimeMillis()));
		client = TestObjectFactory.createClient("Client",
				ClientConstants.STATUS_ACTIVE, "1.4.1.1", group, new Date(
						System.currentTimeMillis()));
	}

	private java.sql.Date offSetCurrentDate(int noOfDays) {
		Calendar currentDateCalendar = new GregorianCalendar();
		int year = currentDateCalendar.get(Calendar.YEAR);
		int month = currentDateCalendar.get(Calendar.MONTH);
		int day = currentDateCalendar.get(Calendar.DAY_OF_MONTH);
		currentDateCalendar = new GregorianCalendar(year, month, day - noOfDays);
		return new java.sql.Date(currentDateCalendar.getTimeInMillis());
	}
}
