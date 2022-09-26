import { USER_PATH } from "../../../atoms/STORE";
import axios from "axios";
import { Component } from "react";
import { wait } from "@testing-library/user-event/dist/utils";
import { Link, Navigate } from "react-router-dom";

class SuccessPayment extends Component {
  constructor() {
    super();
    this.id = localStorage.getItem("id");
    this.params = new URLSearchParams(window.location.search);
    this.token = localStorage.getItem("token");
    this.state = {
      toWait: 5,
    };
  }

  onWaiting = async () => {
    wait(1000).then(() => {
      this.setState({ toWait: this.state.toWait - 1 });
      this.onWaiting();
    });
  };

  componentDidMount() {
    this.onWaiting().then();
    axios
      .get(
        `${USER_PATH}/payment/${this.id}/${this.params.get(
          "PayerID"
        )}/${this.params.get("paymentId")}`,
        {
          method: "get",
          headers: {
            authorization: this.token,
            "Access-Control-Allow-Origin": `${window.location.origin}`,
          },
        }
      )
      .then();
  }

  render() {
    if (this.state.toWait <= 0) return <Navigate to={"/"} />;
    else
      return (
        <>
          <div>PLATA ACCEPTATA</div>
          <div>
            <Link to={"/"}>Redirectionare</Link>
          </div>
        </>
      );
  }
}

export default SuccessPayment;
