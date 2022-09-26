import { useState } from "react";
import axios from "axios";
import { BASE_PATH } from "../atoms/STORE";
import FormContainer from "../wrappers/form/container/FormContainer";
import FormCard from "../wrappers/form/card/FormCard";
import { useNavigate } from "react-router-dom";
import classes from "../scss/Mui.module.scss";
import TextField from "@mui/material/TextField";

const defaultFormFields = {
  firstName: "",
  lastName: "",
  email: "",
  password: "",
  confirmPassword: "",
};

// const errors = {
//   password: {
//     value: false,
//     message: "Numele ar trebui sa contina cel putin 4 litere",
//   },
//   confirmPassword: {
//     value: false,
//     message: "Numele ar trebui sa contina cel putin 4 litere",
//   },
//   firstName: {
//     value: false,
//     message: "Numele ar trebui sa contina cel putin 4 litere",
//   },
//   lastName: {
//     value: false,
//     message: "Numele ar trebui sa contina cel putin 4 litere",
//   },
//   email: {
//     value: false,
//     message: "Numele ar trebui sa contina cel putin 4 litere",
//   },
// };

const Register = () => {
  const [formFields, setFormFields] = useState(defaultFormFields);
  const navigate = useNavigate();

  let path = `${BASE_PATH}/api/auth/register`;
  const onSubmit = (e) => {
    if (formFields.password === formFields.confirmPassword) {
      axios
        .post(path, formFields, {
          headers: {
            "Content-Type": "application/json",
            Accept: "application/json",
          },
        })
        .then((response) => {
          console.log(response);
          if (response.data === "Created") navigate("/logare");
        });
    } else {
    }
  };

  const onChange = (e) => {
    setFormFields({ ...formFields, [e.target.name]: e.target.value });
    switch (e.target.name) {
      case "lastName": {
        break;
      }
      case "firstName": {
        break;
      }
      case "password": {
        break;
      }
      case "confirmPassword": {
        break;
      }
      case "email": {
        break;
      }
      default:
        break;
    }
  };
  return (
    <FormContainer>
      <FormCard
        onSubmit={onSubmit}
        title={"Inregistrare"}
        submitMessage={"Inregistrare"}
      >
        <TextField
            fullWidth
            required
            id="outlined-required"
            label="Nume"
            name={"lastName"}
            value={formFields.lastName}
            onChange={onChange}
            color={"success"}
            InputProps={{classes: {
                input: classes.inputProps
              }}}
        />
        <TextField
            fullWidth
            required
            id="outlined-required"
            label="Prenume"
            name={"firstName"}
            value={formFields.firstName}
            onChange={onChange}
            color={"success"}
            InputProps={{classes: {
                input: classes.inputProps
              }}}
        />
        <TextField
            fullWidth
            required
            id="outlined-required"
            label="E-mail"
            name={"email"}
            value={formFields.email}
            onChange={onChange}
            type={"email"}
            color={"success"}
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
            value={formFields.password}
            onChange={onChange}
            color={"success"}
            InputProps={{classes: {
                input: classes.inputProps
              }}}
        />
        <TextField
          fullWidth
          id="outlined-password-input"
          label="Confirmare parola"
          type="password"
          name={"confirmPassword"}
          value={formFields.password}
          onChange={onChange}
          color={"success"}
          InputProps={{classes: {
              input: classes.inputProps
            }}}
      />
      </FormCard>
    </FormContainer>
  );
};

export default Register;
