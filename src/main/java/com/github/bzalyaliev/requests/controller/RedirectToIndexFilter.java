package com.github.bzalyaliev.requests.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * See https://stackoverflow.com/a/52776358/7785738
 */
@Slf4j
@Component
public class RedirectToIndexFilter implements Filter {

	@Override
	public void doFilter(ServletRequest request,
		ServletResponse response,
		FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		String requestURI = req.getRequestURI();

		if (requestURI.startsWith("/api")) {
			chain.doFilter(request, response);
			return;
		}

		if (requestURI.startsWith("/static")) {
			chain.doFilter(request, response);
			return;
		}

		// all requests not api or static will be forwarded to index page.
		request.getRequestDispatcher("/").forward(request, response);
	}

}
