package com.dzerain.inventoryservice.common;

public class DocsText {
  public static class CREAR_ITEM_INVENTARIO {
    public static final String SUMMARY = "Crear item en Inventario";
    public static final String DESCRIPTION =
        "Crea un item en inventario con el ID del producto y la cantidad disponible.";
  }

  public static class LISTAR_INVENTARIO {
    public static final String SUMMARY = "Listar items en Inventario";
    public static final String DESCRIPTION =
        "Lista el inventario de productos con sus respectivos detalles.";
  }

  public static class OBTENER_ITEM_ID {
    public static final String SUMMARY = "Obtener item en por el ID";
    public static final String DESCRIPTION =
        "Obtiene el item en inventario con el ID especificado.";
  }

  public static class OBTENER_PRODUCTO_ID {
    public static final String SUMMARY = "Obtener producto en por el ID";
    public static final String DESCRIPTION = "Obtiene el producto con el ID especificado.";
  }

  public static class ELIMINAR_ITEM_INVENTARIO {
    public static final String SUMMARY = "Eliminar item en Inventario por el ID";
    public static final String DESCRIPTION =
        "Elimina el item en inventario con el ID especificado.";
  }
}
