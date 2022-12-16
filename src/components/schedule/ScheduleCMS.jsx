import { useState, useContext } from "react";
import { ScheduleContext } from "../../context/ScheduleContext";

function ScheduleCMS() {
  const [name, setName] = useState("");
  const [location, setLocation] = useState("");

  const { createSchedule } = useContext(ScheduleContext);

  const handleSubmit = (e) => {
    e.preventDefault();
    createSchedule({ name, location });
    setName("");
    setLocation("");
  };

  return (
    <div className="max-w-md mx-auto">
      <form onSubmit={handleSubmit} className="bg-slate-800 p-10 mb-4 ">
        <h1 className="text-2xl font-bold text-white mb-3">
          No hacer caso a esta plantilla copiada, estoy trabajando en los
          horarios att:Diego
        </h1>
        <input
          placeholder="Nombre de la facultad"
          onChange={(e) => setName(e.target.value)}
          value={name}
          autoFocus="on"
          className="bg-slate-200 p-3 w-full mb-2"
        />
        <textarea
          name="location"
          value={location}
          placeholder="Localización de la facultad"
          onChange={(e) => setLocation(e.target.value)}
          className="bg-slate-200 p-3 w-full mb-2"
        ></textarea>
        <button className="bg-indigo-500 px-3 py-1 text-white">Guardar</button>
      </form>
    </div>
  );
}

export default ScheduleCMS;
