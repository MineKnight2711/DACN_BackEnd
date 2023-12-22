package com.example.dacn.modules.apikey.auth;

import com.example.dacn.entity.ResponseModel;
import com.example.dacn.modules.apikey.ApiKeyService;
import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


class ApiKeyAuthFilter extends OncePerRequestFilter {

    private ApiKeyService apiKeyService;

    ApiKeyAuthFilter(ApiKeyService apiKeyService) {
        this.apiKeyService = apiKeyService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String clientId = request.getHeader("X-Client-Id");
        String apiKey = request.getHeader("X-Api-Key");

        if (request.getRequestURI().contains("/api/transaction")) {
            if (clientId == null || apiKey == null || !apiKeyService.isApiValid(apiKey, clientId)) {
                sendErrorResponse(response);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
    private void sendErrorResponse(HttpServletResponse response) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        response.getWriter()
                .write(new Gson().toJson(new ResponseModel("Unauthorized",
                        "Không thể request api, vui lòng kiêm tra các trường header")));
    }
}