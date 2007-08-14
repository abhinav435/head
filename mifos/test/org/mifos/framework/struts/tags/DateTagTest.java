package org.mifos.framework.struts.tags;

import java.util.Locale;

import javax.servlet.jsp.JspException;
import static org.mifos.framework.TestUtils.assertWellFormedFragment;

import org.dom4j.DocumentException;
import org.hibernate.Session;
import org.mifos.application.master.business.SupportedLocalesEntity;
import org.mifos.application.office.business.OfficeBO;
import org.mifos.application.personnel.business.PersonnelBO;
import org.mifos.application.personnel.util.helpers.PersonnelConstants;
import org.mifos.application.personnel.util.helpers.PersonnelLevel;
import org.mifos.framework.MifosTestCase;
import org.mifos.framework.TestUtils;
import org.mifos.framework.business.util.Name;
import org.mifos.framework.hibernate.helper.HibernateUtil;
import org.mifos.framework.security.util.UserContext;
import org.mifos.framework.util.helpers.TestObjectFactory;

public class DateTagTest extends MifosTestCase {
	
	private DateTag dateTag = new DateTag();
	private Session session;
	
	@Override
	protected void tearDown() throws Exception {
		HibernateUtil.closeSession(session);
	}

	public void testSimpleStyle() throws DocumentException {
		dateTag.setRenderstyle("simple");
		assertWellFormedFragment(dateTag.makeUserFields("asd", "1", "1", "2000", "", "d/m/y").toString());
		assertEquals((
			"<input type=\"text\" id=\"asdDD\" name=\"asdDD\" "
				+ "maxlength=\"2\" size=\"2\" value=\"1\" " 
				+ "style=\"width:1.5em\""
				+ " />\u00a0DD\u00a0<input type=\"text\" "
				+ "id=\"asdMM\" name=\"asdMM\" maxlength=\"2\" size=\"2\" value=\"1\" "
				+ "style=\"width:1.5em\" />"
				+ "\u00a0MM\u00a0"
				+ "<input type=\"text\" id=\"asdYY\" name=\"asdYY\" "
				+ "maxlength=\"4\" size=\"4\" value=\"2000\" " 
				+ "style=\"width:3em\""
				+ " />\u00a0YYYY\u00a0"),dateTag.makeUserFields("asd", "1", "1", "2000", "", "d/m/y").toString());
	}
	
	public void testGetFormat() throws Exception {
		assertEquals("M/d/yy", dateTag.getUserFormat(TestUtils.makeUser()));
	}
	
	public void testFromPersonnel() throws Exception {
		session = HibernateUtil.openSession();
		Integer title = 1;
		Short locale = 1;
		OfficeBO office = TestObjectFactory.getOffice((short) 1);
		PersonnelBO personnel = 
			new PersonnelBO(PersonnelLevel.NON_LOAN_OFFICER, office, 
					title, locale, "ABCDEF", "joex", null,
					null, null, 
					new Name("Joe", "X.", "testFromPersonnel", "user"), 
					null, null, null, null, null, null, null,
					PersonnelConstants.SYSTEM_USER);
		SupportedLocalesEntity ourLocale = 
			(SupportedLocalesEntity) session.get(
				SupportedLocalesEntity.class, locale);
		personnel.setPreferredLocale(ourLocale);
		UserContext userContext = personnel.login("ABCDEF");
		String format = dateTag.getUserFormat(userContext);
		assertEquals("dd/MM/yy", format);
	}

	public void testPrepareOutputString() throws DocumentException {
		dateTag.setKeyhm("keyHm");
		assertEquals("keyHm", dateTag.getKeyhm());

		dateTag.setIsDisabled("Disabled");
		assertEquals("Disabled", dateTag.getIsDisabled());

		dateTag.setIndexed(true);
		assertWellFormedFragment(dateTag.prepareOutputString("asd", "asd", "asd", "asd","asd","asd","asd").toString());
		assertEquals(
			"<input type=\"text\" id=\"asdYY\" name=\"asdYY\" maxlength=\"4\" " +
			"size=\"4\" value=\"asd\" onBlur=\"makeDateString('asdYY','asd','asd')\" " +
			"style=\"width:3em\"" +
			" />\u00a0YYYY\u00a0" +
			"<input type=\"hidden\" id=\"asd\" name=\"asd\" value=\"asd\" />" +
			"<input type=\"hidden\" id=\"asdFormat\" name=\"asdFormat\" value=\"asd\" />" +
			"<input type=\"hidden\" id=\"datePattern\" name=\"datePattern\" value=\"asd\" />",
			dateTag.prepareOutputString("asd", "asd", "asd", "asd","asd","asd","asd").toString());
	}
	
	public void testDoStartTag() throws JspException, DocumentException {
		UserContext userContext = new UserContext(Locale.UK);
		dateTag.setRenderstyle("simple");
		dateTag.setProperty("testdate");
		assertEquals(
			"<!-- simple style -->" +
			"<input type=\"text\" id=\"testdateDD\" name=\"testdateDD\" " +
			"maxlength=\"2\" size=\"2\" value=\"\" " +
			"style=\"width:1.5em\"" +
			" />\u00a0DD\u00a0" +
			"" +
			"<input type=\"text\" id=\"testdateMM\" name=\"testdateMM\" " +
			"maxlength=\"2\" size=\"2\" value=\"\" " +
			"style=\"width:1.5em\"" +
			" />\u00a0MM\u00a0" +
			"" +
			"<input type=\"text\" id=\"testdateYY\" name=\"testdateYY\" " +
			"maxlength=\"4\" size=\"4\" value=\"\" " +
			"style=\"width:3em\"" +
			" />\u00a0YYYY\u00a0", 
			dateTag.render(userContext, null));
		assertWellFormedFragment(dateTag.render(userContext, null));
	}

}
