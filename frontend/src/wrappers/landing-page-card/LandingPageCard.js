import classes from "./LandingPageCard.module.scss";
import {useNavigate} from "react-router-dom";
import {useAtom} from "jotai";
import {BASE_PATH, PRODUCTS} from "../../atoms/STORE";
import axios from "axios";

const LandingPageCard = ({ url, imageUrl, name }) => {
  const navigate = useNavigate()
  const [,setProducts] = useAtom(PRODUCTS)
  const onClick = () => {
    axios
        .get(`${BASE_PATH}/products/category/${name}`)
        .then((r) => {
          setProducts(r.data)
          navigate(url)
        })
  }
  return (
    <div className={classes.categoryCard} onClick={onClick}>
        <div
          className={classes.backgroundImage}
          style={{ backgroundImage: `url(${imageUrl})` }}
        >
          <div className={classes.content}>
            <p className={classes.title}>{name}</p>
            <p className={classes.subtitle}>Vezi produse</p>
          </div>
        </div>
    </div>
  );
};

export default LandingPageCard;
