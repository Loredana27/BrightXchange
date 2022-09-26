const TextInput = ({ name, value, onChange, title, type, error }) => {
  return (
    <>
      {error.value && <h4>{error.message}</h4>}
      <label>
        {title}:{" "}
        <input value={value} onChange={onChange} name={name} type={type} />
      </label>
    </>
  );
};

export default TextInput;