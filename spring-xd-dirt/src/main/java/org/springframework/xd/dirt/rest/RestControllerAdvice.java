/*
 * Copyright 2013 the original author or authors.
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

package org.springframework.xd.dirt.rest;

import org.springframework.hateoas.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Central class for behavior common to all REST controllers.
 * 
 * @author Eric Bottard
 */
@ControllerAdvice
public class RestControllerAdvice {

	/*
	 * Note that any controller-specific exception handler is resolved first. So for example, having a
	 * onException(Exception e) resolver at a controller level will prevent the one from this class to be triggered.
	 */

	/**
	 * Handles the case where client submitted an ill valued request (missing parameter).
	 */
	@ResponseBody
	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public VndErrors onMissingServletRequestParameterException(MissingServletRequestParameterException e) {
		String msg = e.getMessage();
		return new VndErrors("MissingServletRequestParameterException", msg);
	}

	/**
	 * Handles the general error case. Report server-side error.
	 */
	@ResponseBody
	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public VndErrors onException(Exception e) {
		String msg = StringUtils.hasText(e.getMessage()) ? e.getMessage() : e.getClass().getSimpleName();
		return new VndErrors(e.getClass().getSimpleName(), msg);
	}

}
