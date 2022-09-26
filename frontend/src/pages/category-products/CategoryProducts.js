import { useNavigate, useParams } from "react-router-dom";
import Navigation from "../../components/navigation/Navigation";
import ProductsContainer from "../../wrappers/products-container/ProductsContainer";
import { useEffect, useState } from "react";
import Card from "../../wrappers/card/Card";
import axios from "axios";
import {
  BASE_PATH,
  CART_PRODUCTS,
  CART_PRODUCTS_NUMBER,
  PRODUCTS,
  SEARCH_TEXT,
  USER,
  USER_PATH,
} from "../../atoms/STORE";
import Details from "../../wrappers/card/details/Details";
import Detail from "../../wrappers/card/details/Detail";
import { useAtom } from "jotai";
import classes from "../products/Products.module.scss";
import TextField from "@mui/material/TextField";
import classes2 from "../../scss/Mui.module.scss";

const defaultFilter = {
  minPrice: 0,
  maxPrice: 0,
  producers: [],
  categories: [],
};

const CategoryProducts = () => {
  const [user] = useAtom(USER);
  const [, setCartItems] = useAtom(CART_PRODUCTS);
  const [, setCartItemsCount] = useAtom(CART_PRODUCTS_NUMBER);
  const { categoryName } = useParams();
  const [products, setProducts] = useAtom(PRODUCTS);
  const [atomSearchText] = useAtom(SEARCH_TEXT);
  const [simpleFilter, setSimpleFilter] = useState({
    searchText: atomSearchText,
    minPrice: 0,
    maxPrice: 0,
    producers: [],
    categories: [categoryName],
  });
  useNavigate();
  const [availableFilter, setAvailableFilter] = useState(() => {
    let { minPrice, maxPrice, producers } = defaultFilter;
    products.forEach((p) => {
      if (minPrice > p.price || minPrice === 0) {
        minPrice = p.price;
      }
      if (maxPrice < p.price || maxPrice === 0) {
        maxPrice = p.price;
      }
      if (producers.indexOf(p.producer.name) === -1) {
        producers.push(p.producer.name);
      }
    });

    let filterMinPrice = simpleFilter.minPrice;
    let filterMaxPrice = simpleFilter.maxPrice;
    if (minPrice > filterMinPrice) filterMinPrice = minPrice;
    if (maxPrice < filterMaxPrice || filterMaxPrice === 0)
      filterMaxPrice = maxPrice;
    setSimpleFilter({
      ...simpleFilter,
      minPrice: filterMinPrice,
      maxPrice: filterMaxPrice,
    });
    return {
      minPrice: minPrice,
      maxPrice: maxPrice,
      producers: producers,
      categories: [categoryName],
    };
  });

  const onFilter = () => {
    console.log(simpleFilter);
    if (
        simpleFilter.searchText === "" &&
        simpleFilter.minPrice === availableFilter.minPrice &&
        simpleFilter.maxPrice === availableFilter.maxPrice &&
        simpleFilter.producers.length === 0 &&
        simpleFilter.categories.length === 0
    ) {
      axios.get(`${BASE_PATH}/products`).then((r) => {
        setProductsList(r.data);
      });
    } else if (
        simpleFilter.minPrice === availableFilter.minPrice &&
        simpleFilter.maxPrice === availableFilter.maxPrice &&
        simpleFilter.producers.length === 0 &&
        simpleFilter.categories.length === 0
    ) {
      axios
          .get(`${BASE_PATH}/products/search/${simpleFilter.searchText}`)
          .then((r) => {
            setProductsList(r.data);
          });
    } else {
      axios.post(`${BASE_PATH}/products/search`, simpleFilter).then((r) => {
        setProductsList(r.data);
      });
    }
  };

  const url = `${USER_PATH}/cart-item`;
  useEffect(() => {
    onFilter();
  }, [categoryName]);

  const addToCart = (e) => {
    e.preventDefault();
    let data = {
      product: { id: parseInt(e.target.id) },
      client: { id: parseInt(user.id) },
      quantity: 1,
    };
    axios
      .post(url, data, {
        headers: {
          authorization: user.token,
        },
      })
      .then((r) => {
        if (r.status === 200) {
          axios
            .get(`${url}/${user.id}`, {
              headers: {
                authorization: user.token,
              },
            })
            .then((r) => {
              setCartItems(r.data);
              let count = 0;
              for (let item of r.data) {
                count += item.quantity;
              }
              setCartItemsCount(count);
            });
        } else console.log(r.data);
      });
  };

  const setProductsList = (products) => {
    let { minPrice, maxPrice, producers, categories } = availableFilter;
    products.forEach((p) => {
      if (minPrice > p.price || minPrice === 0) {
        minPrice = p.price;
      }
      if (maxPrice < p.price || maxPrice === 0) {
        maxPrice = p.price;
      }
      if (producers.indexOf(p.producer.name) === -1) {
        producers.push(p.producer.name);
      }
      if (categories.indexOf(p.category.name) === -1) {
        categories.push(p.category.name);
      }
    });
    setAvailableFilter({
      minPrice: minPrice,
      maxPrice: maxPrice,
      producers: producers,
      categories: categories,
    });
    let filterMinPrice = simpleFilter.minPrice;
    let filterMaxPrice = simpleFilter.maxPrice;
    if (minPrice > filterMinPrice) filterMinPrice = minPrice;
    if (maxPrice < filterMaxPrice || filterMaxPrice === 0)
      filterMaxPrice = maxPrice;
    setSimpleFilter({
      ...simpleFilter,
      minPrice: filterMinPrice,
      maxPrice: filterMaxPrice,
    });
    setProducts(products);
  };

  const onChange = (e) => {
    switch (e.target.name) {
      case "searchText":
      case "minPrice":
      case "maxPrice":
        setSimpleFilter({ ...simpleFilter, [e.target.name]: e.target.value });
        return;
      case "producers":
        let temp = [];
        if (simpleFilter.producers.indexOf(e.target.value) !== -1) {
          for (const p of simpleFilter.producers) {
            if (p !== e.target.value) temp.push(p);
          }
        } else {
          temp = simpleFilter.producers;
          temp.push(e.target.value);
        }
        setSimpleFilter({ ...simpleFilter, producers: temp });
        break;
      default:
        break;
    }
  };

  return (
    <>
      <Navigation />
      <div className={classes.mainDiv}>
        <h1 className={classes.title}>{categoryName}</h1>
        <div className={classes.filterDiv}>
          <TextField
            className={"textField"}
            id={"outlined-basic"}
            label={"Nume produs"}
            name={"searchText"}
            variant={"outlined"}
            value={simpleFilter.searchText}
            onChange={onChange}
            color={"success"}
            InputProps={{
              classes: {
                input: classes2.inputProps + classes.limitedInput,
              },
            }}
          />
          <TextField
            className={"textField"}
            type={"number"}
            id={"outlined-basic"}
            label={"Pret minim"}
            min={availableFilter.minPrice}
            max={simpleFilter.maxPrice}
            name={"minPrice"}
            variant={"outlined"}
            value={simpleFilter.minPrice}
            onChange={onChange}
            color={"success"}
            InputProps={{
              classes: {
                input: classes2.inputProps + classes.numberInput,
              },
            }}
          />
          <TextField
            className={"textField"}
            type={"number"}
            id={"outlined-basic"}
            label={"Pret maxim"}
            min={simpleFilter.minPrice}
            max={availableFilter.maxPrice}
            name={"maxPrice"}
            variant={"outlined"}
            value={simpleFilter.maxPrice}
            onChange={onChange}
            color={"success"}
            InputProps={{
              classes: {
                input: classes2.inputProps + classes.numberInput,
              },
            }}
          />
          <div className={classes.panel}>
            <p>Producatori</p>
            {availableFilter.producers.map((p) => (
              <label key={`producator_${p}`}>
                <input
                  name={"producers"}
                  type={"checkbox"}
                  value={p}
                  checked={simpleFilter.producers.indexOf(p) !== -1}
                  onChange={onChange}
                />
                {p}
              </label>
            ))}
          </div>
          <button onClick={onFilter}>FILTREAZA</button>
        </div>
        <ProductsContainer>
          {products.map((p) => (
            <Card
              key={`product_${p.id}`}
              imageUrl={p.imageNames[0]}
              title={p.name}
              onAdd={addToCart}
              price={p.price}
              productId={p.id}
              details={
                <Details
                  details={
                    <>
                      {p.specs.map((spec, index) => (
                        <Detail
                          key={`spec_${spec.id}_${index}`}
                          name={spec.categorySpec.name}
                          value={
                            spec.categorySpec.type === "text"
                              ? spec.stringValue
                              : spec.numberValue
                          }
                        />
                      ))}
                    </>
                  }
                />
              }
            />
          ))}
        </ProductsContainer>
      </div>
    </>
  );
};

export default CategoryProducts;
