import React, { useState } from "react";
import axios from "axios";
import { ADMIN_PATH, USER } from "../../atoms/STORE";
import { useAtom } from "jotai";
import FormCard from "../../wrappers/form/card/FormCard";
import FormContainer from "../../wrappers/form/container/FormContainer";
import { useForm } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import Navigation from "../../components/navigation/Navigation";

const AddCategory = () => {
  const [user] = useAtom(USER);
  const { register, handleSubmit } = useForm();

  const navigate = useNavigate();

  const defaultSpecs = [
    { key: "name", name: "nume", type: "text" },
    {
      key: "quantity",
      name: "cantitate",
      type: "number",
    },
    { key: "price", name: "pret", type: "number" },
    { key: "producer", name: "producator", type: "text" },
  ];

  const [state, setState] = useState({
    name: "",
    specs: [],
    count: 3,
  });

  const onNameChange = (e) => {
    setState({ ...state, name: e.target.value });
  };

  const addSpecInput = (e) => {
    e.preventDefault();
    let count = state.specs.length + 1;
    let name = `spec${count}`;
    const newSpecs = state.specs;
    newSpecs.push({ key: name, name: "", type: "text" });
    setState({ ...state, specs: newSpecs, count: count });
  };

  const onSpecChangeValue = (e) => {
    const { name, value } = e.target;
    const newSpecs = state.specs.map((e) => {
      if (e.key === name) {
        return { ...e, name: value };
      } else return e;
    });
    console.log(newSpecs);

    setState({
      ...state,
      specs: newSpecs,
    });
  };

  const onSpecChangeType = (e) => {
    const { name, value } = e.target;
    const newSpecs = state.specs.map((e) => {
      if (e.key === name) {
        return { ...e, type: value };
      } else return e;
    });
    setState({
      ...state,
      specs: newSpecs,
    });
  };

  const onSubmit = (e) => {
    const formData = new FormData();
    formData.append("image", e.file[0]);
    axios
      .post(`${ADMIN_PATH}/category-images`, formData, {
        method: "POST",
        headers: {
          authorization: user.token,
          "Content-Type": "multipart/form-data",
          Accept: "application/json",
        },
      })
      .then(() => {
        axios
          .post(`${ADMIN_PATH}/categories`, state, {
            method: "POST",
            headers: {
              authorization: user.token,
              "Content-Type": "application/json",
              Accept: "application/json",
            },
          })
          .then(() => navigate("/"));
      });
  };

  const onCancel = () => {
    axios
      .get(`${ADMIN_PATH}/category-images/delete`, {
        headers: {
          authorization: user.token,
        },
      })
      .then();
  };

  return (
    <>
      <Navigation />
      <FormContainer>
        <FormCard onSubmit={handleSubmit(onSubmit)} onCancel={onCancel}>
          <div>
            <h1>Adaugare categorie</h1>
          </div>
          <label>
            Numele categoriei:{" "}
            <input
              id={"category-name"}
              name={"category-name"}
              value={state.name}
              onChange={onNameChange}
            />
          </label>
          <h3>Specificatii</h3>
          <div id={"specs"} className={"specs-container"}>
            {defaultSpecs.map((s, index) => (
              <div key={`${s.name}_${index}`}>
                <label>
                  {" "}
                  {index + 1}. nume:{" "}
                  <input
                    type={"text"}
                    name={s.key}
                    value={s.name}
                    className={"spec"}
                    disabled={true}
                  />
                </label>
                <label>
                  {" "}
                  tip:
                  <select
                    name={s.key}
                    value={s.type}
                    disabled={true}
                    className={"type"}
                  >
                    <option value={"text"}>Text</option>
                    <option value={"number"}>Numeric</option>
                  </select>
                </label>
              </div>
            ))}
            {state.specs.map((s, index) => (
              <div key={`${s.key}_${index}`}>
                <label>
                  {" "}
                  {index + 5}. nume:{" "}
                  <input
                    type={"text"}
                    name={s.key}
                    value={s.name}
                    onChange={onSpecChangeValue}
                    className={"spec"}
                  />
                </label>
                <label>
                  {" "}
                  tip:
                  <select
                    name={s.key}
                    value={s.type}
                    className={"type"}
                    onChange={onSpecChangeType}
                  >
                    <option value={"text"}>Text</option>
                    <option value={"number"}>Numeric</option>
                  </select>
                </label>
              </div>
            ))}
          </div>

          {Object.keys(state.specs).length < 30 && (
            <button onClick={addSpecInput}>Add spec</button>
          )}

          <div>
            <input
              type={"file"}
              accept={".jpeg,.jpg,.png"}
              {...register("file")}
            />
          </div>
          {/*<button onClick={onSubmit}>Submit</button>*/}
        </FormCard>
      </FormContainer>
    </>
  );
};

export default AddCategory;
