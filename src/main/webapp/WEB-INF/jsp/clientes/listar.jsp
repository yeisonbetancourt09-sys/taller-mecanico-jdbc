<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Clientes"/>
<c:set var="activo" value="clientes"/>
<c:set var="base" value="${pageContext.request.contextPath}/clientes"/>
<%@ include file="/WEB-INF/jsp/comun/encabezado.jspf" %>

<div class="barra">
  <h2>Clientes</h2>

  <%-- Formulario con método GET: consulta por documento --%>
  <form method="get" action="${base}" class="buscador">
    <input type="text" name="documento" value="<c:out value='${param.documento}'/>" placeholder="Buscar por documento">
    <button type="submit" class="btn btn-sec">Buscar</button>
    <c:if test="${not empty param.documento}">
      <a class="btn btn-sec" href="${base}">Limpiar</a>
    </c:if>
  </form>

  <a class="btn btn-primary" href="${base}?accion=nuevo">+ Nuevo cliente</a>
</div>

<div class="tabla-envoltura">
  <table class="tabla">
    <thead>
      <tr><th>Documento</th><th>Nombre</th><th>Teléfono</th><th>Correo</th><th>Dirección</th><th>Acciones</th></tr>
    </thead>
    <tbody>
      <c:choose>
        <c:when test="${empty clientes}">
          <tr><td colspan="6" class="vacio">No hay clientes para mostrar.</td></tr>
        </c:when>
        <c:otherwise>
          <c:forEach var="cli" items="${clientes}">
            <tr>
              <td><c:out value="${cli.documento}"/></td>
              <td><c:out value="${cli.nombre}"/></td>
              <td><c:out value="${cli.telefono}"/></td>
              <td><c:out value="${cli.email}"/></td>
              <td><c:out value="${cli.direccion}"/></td>
              <td class="acciones">
                <a class="btn btn-sm btn-sec" href="${base}?accion=editar&id=${cli.idCliente}">Editar</a>
                <%-- Formulario con método POST: eliminar --%>
                <form method="post" action="${base}" class="en-linea"
                      onsubmit="return confirm('¿Eliminar a este cliente?');">
                  <input type="hidden" name="accion" value="eliminar">
                  <input type="hidden" name="id" value="${cli.idCliente}">
                  <button type="submit" class="btn btn-sm btn-peligro">Eliminar</button>
                </form>
              </td>
            </tr>
          </c:forEach>
        </c:otherwise>
      </c:choose>
    </tbody>
  </table>
</div>

<%@ include file="/WEB-INF/jsp/comun/pie.jspf" %>
