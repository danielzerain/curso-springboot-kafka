package com.dzerain.orderservice.common;

public class DocsText {
  public static class CREAR_ORDEN {
    public static final String SUMMARY = "Crear Orden de compra";
    public static final String DESCRIPTION =
        "Crea una nueva orden de compra para el producto especificado.";
  }

  public static class LISTAR_ORDENES {
    public static final String SUMMARY = "Listar Ordenes";
    public static final String DESCRIPTION =
        "Lista todas las ordenes con su respectivo estado y cantidad en inventario.";
  }

  public static class OBTENER_ORDEN_ID {
    public static final String SUMMARY = "Obtener Orden en por el ID";
    public static final String DESCRIPTION = "Obtiene la Orden con el ID especificado.";
  }
}
