import classes from "./FormContainer.module.scss";

const FormContainer = (props) => {
  return <div className={classes.formContainer}>{props.children}</div>;
};

export default FormContainer;
