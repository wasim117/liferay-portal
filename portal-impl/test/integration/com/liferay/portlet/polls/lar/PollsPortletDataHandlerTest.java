/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portlet.polls.lar;

import com.liferay.portal.kernel.lar.PortletDataHandler;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.lar.BasePortletDataHandlerTestCase;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.MainServletExecutionTestListener;
import com.liferay.portal.util.PortletKeys;
import com.liferay.portlet.polls.model.PollsChoice;
import com.liferay.portlet.polls.model.PollsQuestion;
import com.liferay.portlet.polls.util.PollsTestUtil;

import org.junit.runner.RunWith;

/**
 * @author Zsolt Berentey
 */
@ExecutionTestListeners(listeners = {MainServletExecutionTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class PollsPortletDataHandlerTest
	extends BasePortletDataHandlerTestCase {

	@Override
	protected void addStagedModels() throws Exception {
		PollsQuestion question = PollsTestUtil.addQuestion(
			stagingGroup.getGroupId());

		PollsChoice choice = PollsTestUtil.addChoice(
			stagingGroup.getGroupId(), question.getQuestionId());

		PollsTestUtil.addVote(
			stagingGroup.getGroupId(), question.getQuestionId(),
			choice.getChoiceId());
	}

	@Override
	protected PortletDataHandler createPortletDataHandler() {
		return new PollsPortletDataHandler();
	}

	@Override
	protected String getPortletId() {
		return PortletKeys.POLLS;
	}

}