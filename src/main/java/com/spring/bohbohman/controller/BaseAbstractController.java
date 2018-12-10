package com.spring.bohbohman.controller;

import com.spring.bohbohman.bean.ApiResponse;
import com.spring.bohbohman.util.BeanMapping;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Nothing here but blank...
 * Waiting for adding code lines in future...
 * @author MingshuJian
 */
@Slf4j
public abstract class BaseAbstractController {

	static final String PRODUCES_APPLICATION_JSON = "application/json";
	static Logger logger = log;
	
	@ModelAttribute
	void preAction(HttpSession session, HttpServletRequest request) {
		try {
//			if (!containsKey(CLIENT_IP_ADDRESS)) {
//				String ipAddress = getIpAddress(request);
//				String macAddress = getMacAddress(ipAddress);
//				setData(CLIENT_IP_ADDRESS, ipAddress);
//				setData(CLIENT_MAC_ADDRESS, macAddress);
//			}
//			if (!containsKey(CURRENT_USER)) {
//				if (isExist(session)) {
//					setData(CURRENT_USER, getLongUserId(session));
//				}
//			}
		} catch (Exception e) {
			log.debug("获取客户端IP地址与MAC地址失败", e);
		}
	}

	protected <T, E> ApiResponse<E> convertApiResponse(Page<T> page, Class<E> c) {
		ApiResponse<E> apiResponse = new ApiResponse();
		apiResponse.setPage(page.getNumber());
		apiResponse.setPage_size(page.getSize());
		apiResponse.setTotal(page.getTotalElements());
		apiResponse.setItems(BeanMapping.mapList(page.getContent(), c));
		return apiResponse;
	}
}
