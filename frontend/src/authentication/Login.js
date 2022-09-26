import { useState } from "react";
import { useAtom } from "jotai";
import { BASE_PATH, USER } from "../atoms/STORE";
import axios from "axios";
import FormContainer from "../wrappers/form/container/FormContainer";
import FormCard from "../wrappers/form/card/FormCard";
import { useNavigate } from "react-router-dom";
import TextField from "@mui/material/TextField";
import classes from "../scss/Mui.module.scss";

const Login = () => {
  const [credentials, setCredentials] = useState({
    email: "",
    password: "",
  });
  const [errorMessage, setErrorMessage] = useState("");
  const navigate = useNavigate();
  const [, setUser] = useAtom(USER);

  const onSubmit = (e) => {
    // e.preventDefault();
    let path = `${BASE_PATH}/api/auth/signin`;
    axios
      .post(path, credentials, {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((response) => {
        if (response.status === 200) {
          const { token, id, name, roles } = response.data;
          setUser({
            token: token,
            id: id,
            name: name,
            roles: roles,
          });
          localStorage.setItem("token", token);
          localStorage.setItem("id", id);
          localStorage.setItem("name", name);
          localStorage.setItem("role", roles[0]);
          navigate("/");
        } else {
          setErrorMessage(response.data);
        }
      });
  };

  const onChange = (e) => {
    e.preventDefault();
    setCredentials({ ...credentials, [e.target.name]: e.target.value });
  };

  return (
    <FormContainer>
      <FormCard onSubmit={onSubmit} title={"Logare"} submitMessage={"Logare"}>
        {errorMessage !== "" && (
          <h5 style={{ color: "red", textAlign: "center" }}>{errorMessage}</h5>
        )}
        <TextField
            fullWidth
            required
            id="outlined-required"
            label="E-mail"
            name={"email"}
            value={credentials.email}
            onChange={onChange}
            color={errorMessage === "Acest e-mail nu exista!" ? "warning" :"success"}
            error={errorMessage === "Acest e-mail nu exista!"}
            InputProps={{classes: {
              input: classes.inputProps
              }}}
        />
        <TextField
            fullWidth
            id="outlined-password-input"
            label="Parola"
            type="password"
            name={"password"}
            value={credentials.password}
            onChange={onChange}
            color={errorMessage === "Parola incorecta!" ? "warning" :"success"}
            error={errorMessage === "Parola incorecta!"}
            InputProps={{classes: {
                input: classes.inputProps
              }}}
        />
      </FormCard>
    </FormContainer>
  );
};

export default Login;
