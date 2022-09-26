import classes from "./HddDetails.module.scss";

const HddDetails = ({ connectionInterface, capacity, unit }) => (
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
    </tbody>
  </table>
);

export default HddDetails;
