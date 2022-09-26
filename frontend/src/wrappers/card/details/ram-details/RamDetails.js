import classes from "./RamDetails.module.scss";

const RamDetails = ({ memoryType, capacity, frequency }) => (
  <table className={classes.customCardDetails}>
    <tbody>
      <tr>
        <td>Tip memorie: {memoryType}</td>
      </tr>
      <tr>
        <td>Capacitate: {capacity} GB</td>
      </tr>
      <tr>
        <td>Frecventa: {frequency}</td>
      </tr>
    </tbody>
  </table>
);

export default RamDetails;
