import "./App.styles.scss";
import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import Footer from "./components/footer/Footer";
import React, { useEffect } from "react";
import AddCategory from "./pages/category-add/AddCategory";
import Login from "./authentication/Login";
import Register from "./authentication/Register";
import AddProduct from "./pages/product-add/AddProduct";
import LandingPage from "./pages/landing-page/LandingPage";
import CategoryProducts from "./pages/category-products/CategoryProducts";
import Product from "./pages/product/Product";
import { useAtom } from "jotai";
import { USER } from "./atoms/STORE";
import "./main.scss";
import CartPage from "./pages/cart/CartPage";
import Products from "./pages/products/Products";
import SuccessPayment from "./pages/payment/success/SuccessPayment";
import FailedPayment from "./pages/payment/fail/FailedPayment";

const App = () => {
  const [user, setUser] = useAtom(USER);

  useEffect(() => {
    let { token, id, name, role } = localStorage;
    setUser({
      token: token,
      id: id,
      name: name,
      roles: [role],
    });
  }, [setUser]);

  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<LandingPage />} />

          <Route
            path="/adauga-categorie"
            element={
              user.roles.indexOf("ADMIN") !== -1 ? (
                <AddCategory />
              ) : (
                <Navigate to={"/"} />
              )
            }
          />
          <Route
            path="/adauga-produs"
            element={
              user.roles.indexOf("ADMIN") !== -1 ? (
                <AddProduct />
              ) : (
                <Navigate to={"/"} />
              )
            }
          />

          <Route path="/logare" element={<Login />} />
          <Route path="/inregistrare" element={<Register />} />
          <Route path="/cos-cumparaturi" element={<CartPage />} />
          <Route path="/plata/efectuata" element={<SuccessPayment />} />
          <Route path="/plata/neefectuata" element={<FailedPayment />} />
          <Route
            path={"/categorii/:categoryName"}
            element={<CategoryProducts />}
          />
          <Route path={"/produse/:productName"} element={<Product />} />
          <Route path={"/produse"} element={<Products />} />
          <Route path="*" element={<div>404</div>}></Route>
        </Routes>
      </BrowserRouter>
      <Footer />
    </>
  );
};

export default App;
