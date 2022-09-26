import { useEffect, useState } from "react";
import "../../scss/first_page.scss";
import ProductsContainer from "../../wrappers/products-container/ProductsContainer";
import LandingPageCard from "../../wrappers/landing-page-card/LandingPageCard";
import Navigation from "../../components/navigation/Navigation";
import TextField from '@mui/material/TextField'
import classes from "../../scss/Mui.module.scss";

const LandingPage = ({ user }) => {
  const [categories, setCategories] = useState([]);
  const [state, setState] = useState({ name: "" });
  useEffect(() => {
    if (state.name.trim() === "") {
      fetch("http://localhost:8888/categories", { method: "GET" })
        .then((response) => response.json())
        .then((re) => setCategories(re));
    } else {
      fetch(`http://localhost:8888/categories/search/${state.name}`, {
        method: "GET",
      })
        .then((response) => response.json())
        .then((re) => {
          console.log(re);
          setCategories(re);
        });
    }
  }, [state]);

  const searchChange = (event) => {
    setState({
      name: event.target.value,
    });
  };

  return (
    <div className={"mainDiv"}>
      <Navigation actualUser={user} />
      <div className={"searchDiv"}>
          <TextField className={"textField"} id={"outlined-basic"} label={"Cautati categoria dorita"} variant={"outlined"} value={state.name} onChange={searchChange} color={"success"} InputProps={{classes: {
                  input: classes.inputProps
              }}}/>
      </div>
      <ProductsContainer key={"categories"}>
        {categories.map((category, index) => (
          <LandingPageCard
            key={`Category_${index}`}
            url={`/categorii/${category.name}`}
            imageUrl={category.image.imageUrl}
            name={category.name}
          />
        ))}
      </ProductsContainer>
    </div>
  );
};

export default LandingPage;
