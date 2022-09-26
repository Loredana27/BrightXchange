import classes from "./SddDetails.module.scss";

const SsdDetails = ({
  connectionInterface,
  capacity,
  unit,
  writeSpeed,
  readSpeed,
}) => (
  <table className={classes.customCardDetails}>
    <tbody>
      <tr>
        <td>Interfata: {connectionInterface}</td>
      </tr>
      <tr>
        <td>
          Capacitate: {capacity} {unit}
        </td>
      </tr>
      <tr>
        <td>Viteza scriere: {writeSpeed}</td>
      </tr>
      <tr>
        <td>Viteza citire: {readSpeed}</td>
      </tr>
    </tbody>
  </table>
);

export default SsdDetails;
