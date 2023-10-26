package com.example.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.TimeZone;

@WebServlet("/time")
public class TimeServlet extends HttpServlet {
    public static final String DEFAULT_TIMEZONE = "UTC";
    public static final int MILLISECONDS_TO_HOUR = 3600000;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        try (PrintWriter out = response.getWriter()) {
            // Отримуємо значення параметра timezone з запиту
            String timezoneParam = request.getParameter("timezone");
            TimeZone timeZone = TimeZone.getTimeZone(DEFAULT_TIMEZONE);

            if (timezoneParam != null ) {
                String stringZoneID = timezoneParam.substring(3).trim();

                int zoneId = Integer.parseInt(stringZoneID);
                timeZone.setRawOffset(zoneId * MILLISECONDS_TO_HOUR);
            }

            // Отримуємо поточний час та форматуємо його
            Date currentDate = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z");
            sdf.setTimeZone(timeZone);
            String formattedDate = sdf.format(currentDate);

            // Виводимо HTML сторінку
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Current Time (UTC)</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Current Time (UTC)</h1>");
            out.println("<p>" + formattedDate + "</p>");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
