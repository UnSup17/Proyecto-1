import ScheduleMenu from "../../components/adminHome/ScheduleMenu";
import DataCMSMenu from "../../components/adminHome/DataCMSMenu";
import HeaderAdmin from "../../components/adminHome/HeaderAdmin";

function AppMenu() {
  return (
    <>
      <div className="h-full flex flex-col bg-gray-100 dark:bg-paleta2-claro shadow-xl">
        <HeaderAdmin></HeaderAdmin>
        <div className="md:mx-10 lg:mx-24 xl:mx-40">
          <ScheduleMenu></ScheduleMenu>
          <DataCMSMenu></DataCMSMenu>
        </div>
      </div>
    </>
  );
}

export default AppMenu;
