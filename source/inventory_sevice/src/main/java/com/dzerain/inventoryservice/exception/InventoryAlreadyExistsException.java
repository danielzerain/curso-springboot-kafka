package com.dzerain.inventoryservice.exception;

public class InventoryAlreadyExistsException extends RuntimeException {

  public InventoryAlreadyExistsException(String name) {
    super("El producto " + name + " ya existe");
  }
}
