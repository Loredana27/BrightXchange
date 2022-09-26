import { useState } from "react";

const ProductSpecInput = ({ specif }) => {
  const onChangeSpecif = (e) => {
    setSpecification(e.target.value);
  };
  const [specification, setSpecification] = useState({ specification: "" });
  return (
    <div>
      <label>
        Specification name :
        <input
          type={specif.type}
          className={"spec"}
          onChange={onChangeSpecif}
          value={specification}
        />
      </label>
    </div>
  );
};
export default ProductSpecInput;