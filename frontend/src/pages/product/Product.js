import { useParams } from "react-router-dom";

const Product = () => {
  const { productName } = useParams();

  return <div>THIS IS PRODUCT {productName}</div>;
};

export default Product;