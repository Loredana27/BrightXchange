import { atom } from "jotai";

export const BASE_PATH = "http://localhost:8888";
export const USER_PATH = "http://localhost:8888/api/user";
export const ADMIN_PATH = "http://localhost:8888/api/admin";

export const USER = atom({
  token: undefined,
  id: undefined,
  name: undefined,
  roles: [],
});
export const CART_PRODUCTS = atom([]);
export const PRODUCTS = atom([]);
export const AVAILABLE_FILTERS = atom({
  minPrice: 0,
  maxPrice: 0,
  producers: [],
  categories: [],
});
export const CART_PRODUCTS_NUMBER = atom(0);
export const ORDERS = atom([]);

export const SEARCH_TEXT = atom("");
