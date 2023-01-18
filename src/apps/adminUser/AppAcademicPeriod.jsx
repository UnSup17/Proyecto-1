import AcademicPeriodList from "../../components/academicPeriod/AcademicPeriodList";
import AcademicPeriodForm from "../../components/academicPeriod/AcademicPeriodForm";

export default function AppResource() {
  return (
    <main className="bg-fondo2 h-full">
      <div className="container mx-auto p-10">
        <AcademicPeriodForm />
        <AcademicPeriodList />
      </div>
    </main>
  );
}
