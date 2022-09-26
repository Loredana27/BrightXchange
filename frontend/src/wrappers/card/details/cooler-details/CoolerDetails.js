import classes from "./CoolerDetails.module.scss";

const CoolerDetails = ({ dimensions, description }) => (
  <table className={classes.customCardDetails}>
    <tbody>
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

export default CoolerDetails;
