import { Component } from "react";
import { wait } from "@testing-library/user-event/dist/utils";
import { Link, Navigate } from "react-router-dom";

class FailedPayment extends Component {
  constructor(props) {
    super(props);
    this.state = {
      toWait: 5,
    };
  }

  waiting = async () => {
    wait(1000).then(() => {
      this.setState({ toWait: this.state.toWait - 1 });
      if (this.state.toWait !== 0) this.waiting();
    });
  };

  isRedirected = () => {
    return this.state.toWait === 0;
  };

  componentDidMount() {
    this.waiting().then();
  }

  render() {
    if (this.state.toWait === 0) {
      return <Navigate to={"/"} />;
    } else {
      return (
        <>
          <div>PLATA NEEFECTUATA</div>
          <div>
            <Link to={"/"}>Redirectionare</Link>
          </div>
        </>
      );
    }
  }
}

export default FailedPayment;
