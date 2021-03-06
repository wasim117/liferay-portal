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

package com.liferay.portal.kernel.portlet;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HttpUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.util.WebKeys;
import com.liferay.portal.kernel.xml.simple.Element;
import com.liferay.portal.theme.ThemeDisplay;

import java.io.Serializable;

import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.portlet.ActionRequest;
import javax.portlet.MimeResponse;
import javax.portlet.PortletMode;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import javax.portlet.PortletSession;
import javax.portlet.PortletURL;
import javax.portlet.RenderRequest;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceURL;
import javax.portlet.WindowState;
import javax.portlet.WindowStateException;

/**
 * @author Shuyang Zhou
 */
public class PortletRequestModel implements Serializable {

	public PortletRequestModel(
		PortletRequest portletRequest, PortletResponse portletResponse) {

		_containerNamespace = portletRequest.getContextPath();
		_contentType = portletRequest.getResponseContentType();
		_serverName = portletRequest.getServerName();
		_serverPort = portletRequest.getServerPort();
		_secure = portletRequest.isSecure();
		_authType = portletRequest.getAuthType();
		_remoteUser = portletRequest.getRemoteUser();
		_contextPath = portletRequest.getContextPath();
		_locale = portletRequest.getLocale();
		_portletMode = portletRequest.getPortletMode();
		_portletSessionId = portletRequest.getRequestedSessionId();
		_scheme = portletRequest.getScheme();
		_windowState = portletRequest.getWindowState();

		if (portletRequest instanceof ActionRequest) {
			_lifecycle = RenderRequest.ACTION_PHASE;
		}
		else if (portletRequest instanceof RenderRequest) {
			_lifecycle = RenderRequest.RENDER_PHASE;
		}
		else if (portletRequest instanceof ResourceRequest) {
			_lifecycle = RenderRequest.RESOURCE_PHASE;
		}

		if (portletResponse instanceof MimeResponse) {
			MimeResponse mimeResponse = (MimeResponse)portletResponse;

			_portletNamespace = mimeResponse.getNamespace();

			try {
				PortletURL actionURL = mimeResponse.createActionURL();

				_actionURL = actionURL.toString();
			}
			catch (IllegalStateException ise) {
				if (_log.isWarnEnabled()) {
					_log.warn(ise.getMessage());
				}
			}

			try {
				PortletURL renderURL = mimeResponse.createRenderURL();

				_renderURL = renderURL.toString();

				try {
					renderURL.setWindowState(LiferayWindowState.EXCLUSIVE);

					_renderURLExclusive = renderURL.toString();
				}
				catch (WindowStateException wse) {
				}

				try {
					renderURL.setWindowState(LiferayWindowState.MAXIMIZED);

					_renderURLMaximized = renderURL.toString();
				}
				catch (WindowStateException wse) {
				}

				try {
					renderURL.setWindowState(LiferayWindowState.MINIMIZED);

					_renderURLMinimized = renderURL.toString();
				}
				catch (WindowStateException wse) {
				}

				try {
					renderURL.setWindowState(LiferayWindowState.NORMAL);

					_renderURLNormal = renderURL.toString();
				}
				catch (WindowStateException wse) {
				}

				try {
					renderURL.setWindowState(LiferayWindowState.POP_UP);

					_renderURLPopUp = renderURL.toString();
				}
				catch (WindowStateException wse) {
				}
			}
			catch (IllegalStateException ise) {
				if (_log.isWarnEnabled()) {
					_log.warn(ise.getMessage());
				}
			}

			ResourceURL resourceURL = mimeResponse.createResourceURL();

			String resourceURLString = HttpUtil.removeParameter(
				resourceURL.toString(), _portletNamespace + "struts_action");

			resourceURLString = HttpUtil.removeParameter(
				resourceURLString, _portletNamespace + "redirect");

			_resourceURL = resourceURL.toString();
		}

		ThemeDisplay themeDisplay = (ThemeDisplay)portletRequest.getAttribute(
			WebKeys.THEME_DISPLAY);

		if (themeDisplay != null) {
			_themeDisplayModel = new ThemeDisplayModel(themeDisplay);
		}

		_parameters = new HashMap<String, String[]>(
			portletRequest.getParameterMap());

		_attributes = new HashMap<String, Object>();

		Enumeration<String> enumeration = portletRequest.getAttributeNames();

		while (enumeration.hasMoreElements()) {
			String name = enumeration.nextElement();

			Object value = portletRequest.getAttribute(name);

			_attributes.put(name, value);
		}

		PortletSession portletSession = portletRequest.getPortletSession();

		try {
			_portletScopeSessioAttributes = portletSession.getAttributeMap(
				PortletSession.PORTLET_SCOPE);

			_applicationScopeSessionAttributes = portletSession.getAttributeMap(
				PortletSession.APPLICATION_SCOPE);
		}
		catch (IllegalStateException ise) {
			if (_log.isWarnEnabled()) {
				_log.warn(ise.getMessage());
			}
		}
	}

	public String getActionURL() {
		return _actionURL;
	}

	public Map<String, Object> getApplicationScopeSessionAttributes() {
		return _applicationScopeSessionAttributes;
	}

	public Map<String, Object> getAttributes() {
		return _attributes;
	}

	public String getAuthType() {
		return _authType;
	}

	public String getContainerNamespace() {
		return _containerNamespace;
	}

	public String getContentType() {
		return _contentType;
	}

	public String getContextPath() {
		return _contextPath;
	}

	public String getLifecycle() {
		return _lifecycle;
	}

	public Locale getLocale() {
		return _locale;
	}

	public Map<String, String[]> getParameters() {
		return _parameters;
	}

	public PortletMode getPortletMode() {
		return _portletMode;
	}

	public String getPortletNamespace() {
		return _portletNamespace;
	}

	public Map<String, Object> getPortletScopeSessioAttributes() {
		return _portletScopeSessioAttributes;
	}

	public String getPortletSessionId() {
		return _portletSessionId;
	}

	public String getRemoteUser() {
		return _remoteUser;
	}

	public String getRenderURL() {
		return _renderURL;
	}

	public String getRenderURLExclusive() {
		return _renderURLExclusive;
	}

	public String getRenderURLMaximized() {
		return _renderURLMaximized;
	}

	public String getRenderURLMinimized() {
		return _renderURLMinimized;
	}

	public String getRenderURLNormal() {
		return _renderURLNormal;
	}

	public String getRenderURLPopUp() {
		return _renderURLPopUp;
	}

	public String getResourceURL() {
		return _resourceURL;
	}

	public String getScheme() {
		return _scheme;
	}

	public String getServerName() {
		return _serverName;
	}

	public int getServerPort() {
		return _serverPort;
	}

	public ThemeDisplayModel getThemeDisplayModel() {
		return _themeDisplayModel;
	}

	public WindowState getWindowState() {
		return _windowState;
	}

	public boolean isSecure() {
		return _secure;
	}

	public String toXML() {
		Element requestElement = new Element("request");

		requestElement.addElement("container-type", "portlet");
		requestElement.addElement("container-namespace", _contextPath);
		requestElement.addElement("content-type", _contentType);
		requestElement.addElement("server-name", _serverName);
		requestElement.addElement("server-port", _serverPort);
		requestElement.addElement("secure", _secure);
		requestElement.addElement("auth-type", _authType);
		requestElement.addElement("remote-user", _remoteUser);
		requestElement.addElement("context-path", _contextPath);
		requestElement.addElement("locale", _locale);
		requestElement.addElement("portlet-mode", _portletMode);
		requestElement.addElement("portlet-session-id", _portletSessionId);
		requestElement.addElement("scheme", _scheme);
		requestElement.addElement("window-state", _windowState);
		requestElement.addElement("lifecycle", _lifecycle);

		if (_portletNamespace != null) {
			requestElement.addElement("portlet-namespace", _portletNamespace);

			if (_actionURL != null) {
				requestElement.addElement("action-url", _actionURL);
			}

			if (_renderURL != null) {
				requestElement.addElement("render-url", _renderURL);

				if (_renderURLExclusive != null) {
					requestElement.addElement(
						"render-url-exclusive", _renderURLExclusive);
				}

				if (_renderURLMaximized != null) {
					requestElement.addElement(
						"render-url-maximized", _renderURLMaximized);
				}

				if (_renderURLMinimized != null) {
					requestElement.addElement(
						"render-url-minimized", _renderURLMinimized);
				}

				if (_renderURLNormal != null) {
					requestElement.addElement(
						"render-url-normal", _renderURLNormal);
				}

				if (_renderURLPopUp != null) {
					requestElement.addElement(
						"render-url-pop-up", _renderURLPopUp);
				}
			}

			requestElement.addElement("resource-url", _resourceURL);
		}

		if (_themeDisplayModel != null) {
			Element themeDisplayElement = requestElement.addElement(
				"theme-display");

			themeDisplayElement.addElement(
				"cdn-host", _themeDisplayModel.getCdnHost());
			themeDisplayElement.addElement(
				"company-id", _themeDisplayModel.getCompanyId());
			themeDisplayElement.addElement(
				"do-as-user-id", _themeDisplayModel.getDoAsUserId());
			themeDisplayElement.addElement(
				"i18n-language-id", _themeDisplayModel.getI18nLanguageId());
			themeDisplayElement.addElement(
				"i18n-path", _themeDisplayModel.getI18nPath());
			themeDisplayElement.addElement(
				"language-id", _themeDisplayModel.getLanguageId());
			themeDisplayElement.addElement(
				"locale", _themeDisplayModel.getLocale());
			themeDisplayElement.addElement(
				"path-context", _themeDisplayModel.getPathContext());
			themeDisplayElement.addElement(
				"path-friendly-url-private-group",
				_themeDisplayModel.getPathFriendlyURLPrivateGroup());
			themeDisplayElement.addElement(
				"path-friendly-url-private-user",
				_themeDisplayModel.getPathFriendlyURLPrivateUser());
			themeDisplayElement.addElement(
				"path-friendly-url-public",
				_themeDisplayModel.getPathFriendlyURLPublic());
			themeDisplayElement.addElement(
				"path-image", _themeDisplayModel.getPathImage());
			themeDisplayElement.addElement(
				"path-main", _themeDisplayModel.getPathMain());
			themeDisplayElement.addElement(
				"path-theme-images", _themeDisplayModel.getPathThemeImages());
			themeDisplayElement.addElement(
				"plid", _themeDisplayModel.getPlid());
			themeDisplayElement.addElement(
				"portal-url", _themeDisplayModel.getPortalURL());
			themeDisplayElement.addElement(
				"real-user-id", _themeDisplayModel.getRealUserId());
			themeDisplayElement.addElement(
				"scope-group-id", _themeDisplayModel.getScopeGroupId());
			themeDisplayElement.addElement(
				"secure", _themeDisplayModel.isSecure());
			themeDisplayElement.addElement(
				"server-name", _themeDisplayModel.getServerName());
			themeDisplayElement.addElement(
				"server-port", _themeDisplayModel.getServerPort());
			themeDisplayElement.addElement(
				"time-zone", _themeDisplayModel.getTimeZone().getID());
			themeDisplayElement.addElement(
				"url-portal", _themeDisplayModel.getURLPortal());
			themeDisplayElement.addElement(
				"user-id", _themeDisplayModel.getUserId());

			PortletDisplayModel portletDisplayModel =
				_themeDisplayModel.getPortletDisplayModel();

			if (portletDisplayModel != null) {
				Element portletDisplayElement = themeDisplayElement.addElement(
					"portlet-display");

				portletDisplayElement.addElement(
					"id", portletDisplayModel.getId());
				portletDisplayElement.addElement(
					"instance-id", portletDisplayModel.getInstanceId());
				portletDisplayElement.addElement(
					"portlet-name", portletDisplayModel.getPortletName());
				portletDisplayElement.addElement(
					"resource-pk", portletDisplayModel.getResourcePK());
				portletDisplayElement.addElement(
					"root-portlet-id", portletDisplayModel.getRootPortletId());
				portletDisplayElement.addElement(
					"title", portletDisplayModel.getTitle());
			}
		}

		Element parametersElement = requestElement.addElement("parameters");

		for (Map.Entry<String, String[]> entry : _parameters.entrySet()) {
			Element parameterElement = parametersElement.addElement(
				"parameter");

			parameterElement.addElement("name", entry.getKey());

			for (String value : entry.getValue()) {
				parameterElement.addElement("value", value);
			}
		}

		Element attributesElement = requestElement.addElement("attributes");

		for (Map.Entry<String, Object> entry : _attributes.entrySet()) {
			String name = entry.getKey();

			if (!_isValidAttributeName(name)) {
				continue;
			}

			Object value = entry.getValue();

			if (!_isValidAttributeValue(value)) {
				continue;
			}

			Element attributeElement = attributesElement.addElement(
				"attribute");

			attributeElement.addElement("name", name);
			attributeElement.addElement("value", value);
		}

		Element portletSessionElement = requestElement.addElement(
			"portlet-session");

		attributesElement = portletSessionElement.addElement(
			"portlet-attributes");

		for (Map.Entry<String, Object> entry :
				_portletScopeSessioAttributes.entrySet()) {

			String name = entry.getKey();

			if (!_isValidAttributeName(name)) {
				continue;
			}

			Object value = entry.getValue();

			if (!_isValidAttributeValue(value)) {
				continue;
			}

			Element attributeElement = attributesElement.addElement(
				"attribute");

			attributeElement.addElement("name", name);
			attributeElement.addElement("value", value);
		}

		attributesElement = portletSessionElement.addElement(
			"application-attributes");

		for (Map.Entry<String, Object> entry :
				_applicationScopeSessionAttributes.entrySet()) {

			String name = entry.getKey();

			if (!_isValidAttributeName(name)) {
				continue;
			}

			Object value = entry.getValue();

			if (!_isValidAttributeValue(value)) {
				continue;
			}

			Element attributeElement = attributesElement.addElement(
				"attribute");

			attributeElement.addElement("name", name);
			attributeElement.addElement("value", value);
		}

		return requestElement.toXMLString();
	}

	protected PortletRequestModel() {
	}

	private static boolean _isValidAttributeName(String name) {
		if (StringUtil.equalsIgnoreCase(name, "j_password") ||
			StringUtil.equalsIgnoreCase(name, "LAYOUT_CONTENT") ||
			StringUtil.equalsIgnoreCase(name, "LAYOUTS") ||
			StringUtil.equalsIgnoreCase(name, "PORTLET_RENDER_PARAMETERS") ||
			StringUtil.equalsIgnoreCase(name, "USER_PASSWORD") ||
			name.startsWith("javax.") ||
			name.startsWith("liferay-ui:")) {

			return false;
		}
		else {
			return true;
		}
	}

	private static boolean _isValidAttributeValue(Object obj) {
		if (obj == null) {
			return false;
		}
		else if (obj instanceof Collection<?>) {
			Collection<?> col = (Collection<?>)obj;

			return !col.isEmpty();
		}
		else if (obj instanceof Map<?, ?>) {
			Map<?, ?> map = (Map<?, ?>)obj;

			return !map.isEmpty();
		}
		else {
			String objString = String.valueOf(obj);

			if (Validator.isNull(objString)) {
				return false;
			}

			String hashCode = StringPool.AT.concat(
				StringUtil.toHexString(obj.hashCode()));

			if (objString.endsWith(hashCode)) {
				return false;
			}

			return true;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(PortletRequestModel.class);

	private String _actionURL;
	private Map<String, Object> _applicationScopeSessionAttributes;
	private Map<String, Object> _attributes;
	private String _authType;
	private String _containerNamespace;
	private String _contentType;
	private String _contextPath;
	private String _lifecycle;
	private Locale _locale;
	private Map<String, String[]> _parameters;
	private PortletMode _portletMode;
	private String _portletNamespace;
	private Map<String, Object> _portletScopeSessioAttributes;
	private String _portletSessionId;
	private String _remoteUser;
	private String _renderURL;
	private String _renderURLExclusive;
	private String _renderURLMaximized;
	private String _renderURLMinimized;
	private String _renderURLNormal;
	private String _renderURLPopUp;
	private String _resourceURL;
	private String _scheme;
	private boolean _secure;
	private String _serverName;
	private int _serverPort;
	private ThemeDisplayModel _themeDisplayModel;
	private WindowState _windowState;

}