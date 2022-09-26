import classes from "./FormCard.module.scss";
import { useNavigate } from "react-router-dom";

const FormCard = (props) => {
  const navigate = useNavigate();
  const onCancel = () => {
    navigate("/");
  };

  const onEnter = (e) => {
    if (e.keyCode === 13) props.onSubmit();
    switch (e.keyCode) {
      case 13:
        props.onSubmit();
        break;
      case 27:
        if (props.onCancel !== undefined) props.onCancel();
        onCancel();
        break;
      default:
        break;
    }
  };

  return (
    <div className={classes.form} onKeyUp={onEnter} autoFocus={true}>
      <h1>{props.title}</h1>

      <div className={classes.content}>{props.children}</div>
      <div>
        <button type="cancel" onClick={onCancel} className={classes.cancel}>
          <span></span>
          {props.cancelMessage !== undefined ? props.cancelMessage : "Anulare"}
        </button>
        <button
          type="submit"
          className={classes.submit}
          onClick={props.onSubmit}
        >
          {props.submitMessage !== undefined ? props.submitMessage : "Salveaza"}
          <span></span>
        </button>
      </div>
    </div>
  );
};

export default FormCard;
