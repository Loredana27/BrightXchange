const Detail = ({ name, value }) => {
  return (
    <tr>
      <td>
        {name}: {value}
      </td>
    </tr>
  );
};

export default Detail;