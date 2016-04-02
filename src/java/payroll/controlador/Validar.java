/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package payroll.controlador;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import payroll.conexion.sqlController;

/**
 *
 * @author Oscar
 */
public class Validar extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            /* TODO output your page here. You may use following sample code. */
            String banco = (String) request.getParameter("banco");
            String cedula = (String) request.getParameter("cedula");
            String fnacimiento = (String) request.getParameter("fnacimiento");

            String[] nacimiento = fnacimiento.split("/");
            String dia = nacimiento[0];
            String mes = nacimiento[1];
            String anio = nacimiento[2];
            String numerocuenta = (String) request.getParameter("numerocuenta");

            String sql = "SELECT * FROM `employes` WHERE `cedula` = '" + cedula + "' AND `numero_cuenta` = '" + numerocuenta + "' AND `fecha_nacimiento` = '" + anio + "-" + mes + "-" + dia + "' AND `banco` = '" + banco + "'";
            sqlController conSql = new sqlController();
            ResultSet rs = conSql.CargarSql(sql);
            int rsCount = 0;
            String nombreArchivo = "";
            if (rs != null) {
                try {
                    while (rs.next()) {

                        nombreArchivo = rs.getString(5);
                        cedula = rs.getString(1);
                        System.out.println("cedula: " + cedula);
                        System.out.println("nombreArchivo: " + nombreArchivo);
                        rsCount++;
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(Validar.class.getName()).log(Level.SEVERE, null, ex);
                }
                if (rsCount == 0) {
                   PrintWriter out = response.getWriter();
                   out.write("0");
                } else {
                    try {
                        String ruta = request.getSession().getServletContext().getRealPath("/WEB-INF/classes/payroll/pdfs/");
                        ruta += "\\" + nombreArchivo;

                        File f2 = new File(ruta);
                        FileInputStream inStream = new FileInputStream(f2);
                        OutputStream outStream = response.getOutputStream();

                        response.setContentType("application/force-download");
                        response.setContentLength((int) f2.length());
                        response.setHeader("Content-Transfer-Encoding", "binary");
                        response.setHeader("Content-Disposition", "attachment; filename=\"" + f2.getName());//fileName

                        byte[] buffer = new byte[4096];
                        int bytesRead = -1;

                        while ((bytesRead = inStream.read(buffer)) != -1) {
                            outStream.write(buffer, 0, bytesRead);
                        }

                        inStream.close();
                        outStream.close();
                    } catch (IOException e) {
                        Logger.getLogger(Validar.class.getName()).log(Level.SEVERE, null, e);
                    } catch (Exception e) {
                        Logger.getLogger(Validar.class.getName()).log(Level.SEVERE, null, e);
                    } finally {
                        //out.close();
                    }
                }
            }
        } finally {
            //System.out.println("ok");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
