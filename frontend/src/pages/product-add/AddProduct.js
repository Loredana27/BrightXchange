import React, { useEffect, useState } from "react";
import axios from "axios";
import { ADMIN_PATH, BASE_PATH, USER } from "../../atoms/STORE";
import { useAtom } from "jotai";
import { useNavigate } from "react-router-dom";
import FormCard from "../../wrappers/form/card/FormCard";
import FormContainer from "../../wrappers/form/container/FormContainer";
import { useForm } from "react-hook-form";
import Navigation from "../../components/navigation/Navigation";

const AddProduct = () => {
  // const [user] = useAtom(USER);
  const [state, setState] = useState({
    name: "",
    quantity: 1,
    price: 1,
    producer: { name: "" },
    specs: [],
  });
  const [categories, setCategories] = useState([
    { id: 0, name: "Alegeti o categorie", specs: [] },
  ]);
  const [activeCategory, setActiveCategory] = useState({
    id: 0,
    name: "Alegeti o categorie",
    specs: [],
  });
  const [user] = useAtom(USER);

  const { register, handleSubmit } = useForm();

  const navigate = useNavigate();

  useEffect(() => {
    fetch(`${BASE_PATH}/categories`, { method: "GET" })
      .then((response) => response.json())
      .then((re) =>
        setCategories([
          { id: 0, name: "Alegeti o categorie", specs: [] },
          ...re,
        ])
      );
  }, []);

  const onChange = (e) => {
    const { name, value, type } = e.target;
    let finalValue;
    if (type === "number") {
      finalValue = parseFloat(value);
    } else finalValue = value;

    if (name !== "producer") setState({ ...state, [name]: finalValue });
    else setState({ ...state, producer: { name: value } });
  };

  const onChangeSpec = (e) => {
    let specs = state.specs.map((elem) => {
      if (elem.categorySpec.name === e.target.name) {
        if (elem.categorySpec.type === "text") {
          elem = { ...elem, stringValue: e.target.value };
        } else {
          elem = { ...elem, numberValue: e.target.value };
        }
      }
      return elem;
    });
    setState({ ...state, specs: specs });
  };

  const onChangeCategory = (e) => {
    for (let elem of categories) {
      if (elem.id === parseInt(e.target.value)) {
        setActiveCategory(elem);
        let specs = [];
        for (let spec of elem.specs) {
          if (spec.type === "text") {
            specs.push({ categorySpec: spec, stringValue: "" });
          } else {
            specs.push({ categorySpec: spec, numberValue: 0 });
          }
        }
        setState({ ...state, specs: specs });
        break;
      }
    }
  };

  const onSubmit = async (e) => {
    let finalState = { ...state, category: activeCategory };
    console.log(finalState);
    for (let image of e.file) {
      await axios.post(
        `${ADMIN_PATH}/product-images`,
        { image: image },
        {
          method: "POST",
          headers: {
            Authorization: user.token,
            "Content-Type": "multipart/form-data",
            Accept: "application/json",
          },
        }
      );
    }
    await axios.post(`${ADMIN_PATH}/products`, finalState, {
      headers: {
        authorization: user.token,
        "Content-Type": "application/json",
        Accept: "application/json",
      },
    });
    navigate("/");
  };
  const onCancel = () => {
    axios
      .get(`${ADMIN_PATH}/product-images/delete`, {
        headers: { authorization: user.token },
      })
      .then();
  };
  return (
    <>
      <Navigation />
      <FormContainer>
        <FormCard onSubmit={handleSubmit(onSubmit)} onCancel={onCancel}>
          <h1>Adauga produs</h1>
          <div>
            {Object.keys(state).map((k) => {
              let type;
              let value;
              let name;
              switch (k) {
                case "producer": {
                  name = "producator";
                  type = "text";
                  value = state.producer.name;
                  break;
                }
                case "name": {
                  name = "nume";
                  type = "text";
                  value = state.name;
                  break;
                }
                case "quantity": {
                  name = "cantitate";
                  type = "number";
                  value = state.quantity;
                  break;
                }
                case "price": {
                  name = "pret";
                  type = "number";
                  value = state.price;
                  break;
                }
                default:
                  break;
              }
              return k !== "specs" ? (
                <div key={`${k}`}>
                  <label>
                    {name}:{" "}
                    <input
                      name={k}
                      value={value}
                      onChange={onChange}
                      type={type}
                    />
                  </label>
                </div>
              ) : null;
            })}
          </div>
          <div>
            <select
              name={"category"}
              value={activeCategory.id}
              onChange={onChangeCategory}
            >
              {categories.map((c) => (
                <option key={`category_${c.id}`} value={c.id}>
                  {c.name}
                </option>
              ))}
            </select>
          </div>
          {state.specs.map((e, index) => (
            <div key={`spec_${index}`}>
              <label>
                {e.categorySpec.name}:{" "}
                <input
                  name={e.categorySpec.name}
                  value={
                    e.categorySpec === "text" ? e.stringValue : e.numberValue
                  }
                  onChange={onChangeSpec}
                  type={e.categorySpec.type}
                />
              </label>
            </div>
          ))}
          <div>
            <input
              type={"file"}
              accept={".jpeg,.jpg,.png"}
              multiple={true}
              {...register("file")}
            />
          </div>
        </FormCard>
      </FormContainer>
    </>
  );
};

export default AddProduct;
