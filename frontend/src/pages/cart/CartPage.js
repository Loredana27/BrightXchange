import { useAtom } from "jotai";
import {
  CART_PRODUCTS,
  CART_PRODUCTS_NUMBER,
  USER,
  USER_PATH,
} from "../../atoms/STORE";
import Navigation from "../../components/navigation/Navigation";
import axios from "axios";
import classes from "./CartPage.module.scss";

const CartPage = () => {
  const [cartItems, setCartItems] = useAtom(CART_PRODUCTS);
  const [, setCountCartItems] = useAtom(CART_PRODUCTS_NUMBER);
  const [user] = useAtom(USER);
  let totalCost = 0;

  const onChange = (e) => {
    console.log(e.target.value);
    if (e.target.value === "") {
      e.target.value = 0;
    }
    axios
      .post(
        `${USER_PATH}/cart-item/update`,
        {
          id: e.target.id,
          quantity: e.target.value,
        },
        { headers: { authorization: user.token } }
      )
      .then(() => {
        axios
          .get(`${USER_PATH}/cart-item/${user.id}`, {
            headers: { authorization: user.token },
          })
          .then((r) => {
            setCartItems(r.data);
            let sum = 0;
            for (const item of r.data) {
              sum += item.quantity;
            }
            setCountCartItems(sum);
          });
      });
  };
  const payment = () => {
    axios
      .get(`${USER_PATH}/payment/${user.id}`, {
        headers: {
          authorization: user.token,
        },
      })
      .then((r) => {
        console.log(r);
        window.location.href = r.data;
      });
  };
  return (
    <>
      <Navigation />
      <div className={classes.container}>
        <table style={{ marginTop: "120px" }}>
          <thead>
            <tr>
              <th></th>
              <th>Nume Produs</th>
              <th>Pret</th>
              <th>Cantitate</th>
              <th>Pret total</th>
            </tr>
          </thead>
          <tbody>
            {cartItems.map((item, index) => {
              let cost = item.quantity * item.product.price;
              totalCost += cost;
              return (
                <tr key={`item_${index}`}>
                  <td>{index + 1}</td>
                  <td>{item.product.name}</td>
                  <td>{item.product.price} RON</td>
                  <td>
                    <input
                      id={item.id}
                      onChange={onChange}
                      type={"number"}
                      min={0}
                      minLength={1}
                      value={item.quantity}
                    />
                  </td>
                  <td>{cost} RON</td>
                </tr>
              );
            })}
            <tr>
              <td style={{ height: "20px" }}></td>
            </tr>
            <tr>
              <td>TOTAL:</td>
              <td></td>
              <td></td>
              <td></td>
              <td>{totalCost} RON</td>
            </tr>
            <tr>
              <td></td>
              <td></td>
              <td></td>
              <td></td>
              <td>
                <button onClick={payment}>Plateste</button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </>
  );
};

export default CartPage;
