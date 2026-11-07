<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="titulo" value="Vehículo"/>
<c:set var="activo" value="vehiculos"/>
<c:set var="base" value="${pageContext.request.contextPath}/vehiculos"/>
<%@ include file="/WEB-INF/jsp/comun/encabezado.jspf" %>

<h2><c:out value="${vehiculo.idVehiculo == 0 ? 'Nuevo vehículo' : 'Editar vehículo'}"/></h2>

<c:if test="${not empty error}">
  <div class="alerta alerta-error"><c:out value="${error}"/></div>
</c:if>

<c:choose>
  <c:when test="${empty clientes}">
    <div class="alerta alerta-error">
      Primero debe registrar al menos un cliente para poder asociarle un vehículo.
    </div>
    <a class="btn btn-primary" href="${pageContext.request.contextPath}/clientes?accion=nuevo">Registrar cliente</a>
  </c:when>
  <c:otherwise>
    <%-- Formulario con método POST: insertar o actualizar --%>
    <form method="post" action="${base}" class="formulario">
      <input type="hidden" name="accion" value="guardar">
      <input type="hidden" name="idVehiculo" value="${vehiculo.idVehiculo}">

      <div class="campo">
        <label for="placa">Placa *</label>
        <input type="text" id="placa" name="placa" maxlength="10" required
               value="<c:out value='${vehiculo.placa}'/>">
      </div>
      <div class="campo">
        <label for="marca">Marca *</label>
        <input type="text" id="marca" name="marca" maxlength="50" required
               value="<c:out value='${vehiculo.marca}'/>">
      </div>
      <div class="campo">
        <label for="modelo">Modelo *</label>
        <input type="text" id="modelo" name="modelo" maxlength="50" required
               value="<c:out value='${vehiculo.modelo}'/>">
      </div>
      <div class="campo">
        <label for="anio">Año *</label>
        <input type="number" id="anio" name="anio" min="1950" max="2100" required
               value="${vehiculo.anio == 0 ? '' : vehiculo.anio}">
      </div>
      <div class="campo">
        <label for="color">Color</label>
        <input type="text" id="color" name="color" maxlength="30"
               value="<c:out value='${vehiculo.color}'/>">
      </div>
      <div class="campo">
        <label for="idCliente">Propietario *</label>
        <select id="idCliente" name="idCliente" required>
          <option value="">Seleccione un cliente</option>
          <c:forEach var="cli" items="${clientes}">
            <option value="${cli.idCliente}" ${cli.idCliente == vehiculo.idCliente ? 'selected' : ''}>
              <c:out value="${cli.nombre}"/> (<c:out value="${cli.documento}"/>)
            </option>
          </c:forEach>
        </select>
      </div>

      <div class="botones">
        <a class="btn btn-sec" href="${base}">Cancelar</a>
        <button type="submit" class="btn btn-primary">Guardar</button>
      </div>
    </form>
  </c:otherwise>
</c:choose>

<%@ include file="/WEB-INF/jsp/comun/pie.jspf" %>
