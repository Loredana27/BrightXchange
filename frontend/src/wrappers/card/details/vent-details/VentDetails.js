import classes from "./VentDetails.module.scss";

const VentDetails = ({
  rotationSpeed,
  ventsNumber,
  description,
  dimensions,
  ventDimensions,
}) => (
  <table className={classes.customCardDetails}>
    <tbody>
      <tr>
        <td>Viteza de rotatie: {rotationSpeed}</td>
      </tr>
      <tr>
        <td>Numar ventilatoare: {ventsNumber}</td>
      </tr>
      <tr>
        <td>Dimensiuni ventilator: {ventDimensions}</td>
      </tr>
      <tr>
        <td>Dimensiuni: {dimensions}</td>
      </tr>
      <tr>
        <td>
          Descriere: <p>{description}</p>
        </td>
      </tr>
    </tbody>
  </table>
);

export default VentDetails;
